package com.example.wound.service;

import com.example.wound.dto.InjuryPhaseDTO;
import com.example.wound.entity.PatientEntity;
import com.example.wound.entity.InjuryEntity;
import com.example.wound.entity.InjuryPhaseEntity;
import com.example.wound.repository.PatientRepository;
import com.example.wound.repository.InjuryPhaseRepository;
import com.example.wound.repository.InjuryRepository;
import com.example.wound.rest.requests.AddInjuryPhaseRequest;
import com.example.wound.rest.requests.AddInjuryRequest;
import com.example.wound.rest.requests.UpdateInjuryPhaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class InjuryService {

    @Autowired
    private InjuryRepository injuryRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private InjuryPhaseRepository injuryPhaseRepository;


    public List<InjuryPhaseDTO> getInjuryPhases(Long id) {

        List<InjuryPhaseDTO> injuryPhaseDTOS = new ArrayList<>();

        for(InjuryPhaseEntity phase: injuryRepository.findAllById(id).getInjuryPhases()){
            InjuryPhaseDTO dto = new InjuryPhaseDTO();
            dto.setNotes(phase.getNotes());
            dto.setLength(phase.getLength());
            dto.setDegree(phase.getDegree());
            dto.setId(phase.getId());
            dto.setWidth(phase.getWidth());
            dto.setPhotoDate(phase.getPhotoDate());
            dto.setConditionsTicked(phase.getConditionsTicked());

            // Read the image file into a byte array and set it on the DTO
            try {
                Path path = Paths.get(phase.getImagePath());
                byte[] image = Files.readAllBytes(path);
                dto.setImage(image);

                if (phase.getDrawingPath() != null) {
                    Path drawingPath = Paths.get(phase.getDrawingPath());
                    byte[] drawingData = Files.readAllBytes(drawingPath);
                    dto.setDrawingData(drawingData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            injuryPhaseDTOS.add(dto);
        }

        return injuryPhaseDTOS;
    }

    public String addInjury(AddInjuryRequest request) {
        try {
            PatientEntity patient = patientRepository.findByBarcode(request.getBarcode());
            InjuryEntity injury = new InjuryEntity();
            injury.setPatient(patient);
            injury.setRegion(request.getRegion());
            injury.setLocation(request.getLocation());
            injury.setDate(request.getDate());

            // Add the injury to the patient's list of injuries
            patient.getInjuries().add(injury);

            // Save the patient, which will also save the injury due to the cascade setting
            patientRepository.save(patient);

        } catch (Exception e) {
            return e.toString();
        }

        return "Injury successfully added";
    }

    public String deleteInjury(Long id) {
        try {
            InjuryEntity injury = injuryRepository.findById(id).orElse(null);
            if (injury == null) {
                return "Injury not found";
            }

            // Delete the files associated with each injury phase
            for (InjuryPhaseEntity phase : injury.getInjuryPhases()) {
                if (phase.getImagePath() != null) {
                    Path imagePath = Paths.get(phase.getImagePath());
                    Files.deleteIfExists(imagePath);
                }
                if (phase.getDrawingPath() != null) {
                    Path drawingPath = Paths.get(phase.getDrawingPath());
                    Files.deleteIfExists(drawingPath);
                }
            }

            injuryRepository.delete(injury);
        } catch (Exception e) {
            return e.toString();
        }

        return "Injury successfully deleted";
    }


    public String addInjuryPhase(MultipartFile image, MultipartFile drawingData, AddInjuryPhaseRequest request) {
        try {
            InjuryPhaseEntity injuryPhase = new InjuryPhaseEntity();
            InjuryEntity injury = injuryRepository.findAllById(request.getInjuryId());

            // Set the new fields
            injuryPhase.setPhotoId(request.getPhotoId());
            injuryPhase.setDegree(request.getDegree());
            injuryPhase.setLength(request.getLength());
            injuryPhase.setWidth(request.getWidth());
            injuryPhase.setNotes(request.getNotes());
            injuryPhase.setConditionsTicked(request.getConditionsTicked());

            // Save the image in a file under the "injuryPhases" directory
            String directory = "injuryPhases";
            String fileName = request.getPhotoId() + ".jpg"; // Use the photoId as the file name
            Path path = Paths.get(directory, fileName);
            Files.createDirectories(path.getParent()); // Create the directory if it doesn't exist
            Files.write(path, image.getBytes()); // Write the image data to the file

            // Set the imagePath field to the path of the saved image file
            injuryPhase.setImagePath(path.toString());



            if (drawingData != null && !drawingData.isEmpty()) {
                String drawingFileName = request.getPhotoId() + "Drawing.data"; // Use the photoId as the file name
                Path drawingPath = Paths.get(directory, drawingFileName);
                Files.createDirectories(drawingPath.getParent()); // Create the directory if it doesn't exist
                Files.write(drawingPath, drawingData.getBytes()); // Write the drawing data to the file
                injuryPhase.setDrawingPath(drawingPath.toString());
            }



            injuryPhase.setInjury(injury);
            injuryPhase.setPhotoDate(request.getPhotoDate());
            injury.getInjuryPhases().add(injuryPhase);

            injuryPhaseRepository.save(injuryPhase);
        } catch (Exception e) {
            return e.toString();
        }

        return "Injury phase successfully added";
    }

    public String updateInjuryPhase(Long id, MultipartFile drawingData, UpdateInjuryPhaseRequest request) {
        try {
            InjuryPhaseEntity injuryPhase = injuryPhaseRepository.findById(id).orElse(null);
            if (injuryPhase == null) {
                return "Injury phase not found";
            }

            injuryPhase.setPhotoId(request.getPhotoId());
            injuryPhase.setDegree(request.getDegree());
            injuryPhase.setLength(request.getLength());
            injuryPhase.setWidth(request.getWidth());
            injuryPhase.setNotes(request.getNotes());
            injuryPhase.setConditionsTicked(request.getConditionsTicked());

            if (drawingData != null && !drawingData.isEmpty()) {

                if (injuryPhase.getDrawingPath() != null) {
                    Path oldDrawingPath = Paths.get(injuryPhase.getDrawingPath());
                    Files.deleteIfExists(oldDrawingPath);
                }

                String directory = "injuryPhases";
                String drawingFileName = request.getPhotoId() + "Drawing.data"; // Use the photoId as the file name
                Path drawingPath = Paths.get(directory, drawingFileName);
                Files.createDirectories(drawingPath.getParent()); // Create the directory if it doesn't exist
                Files.write(drawingPath, drawingData.getBytes()); // Write the drawing data to the file
                injuryPhase.setDrawingPath(drawingPath.toString());
            }

            injuryPhase.setPhotoDate(request.getPhotoDate());

            injuryPhaseRepository.save(injuryPhase);
        } catch (Exception e) {
            return e.toString();
        }

        return "Injury phase successfully updated";
    }


    public String deleteInjuryPhase(Long id) {
        try {
            InjuryPhaseEntity injuryPhase = injuryPhaseRepository.findById(id).orElse(null);
            if (injuryPhase == null) {
                return "Injury phase not found";
            }

            // Delete the image and drawing data files
            if (injuryPhase.getImagePath() != null) {
                Path imagePath = Paths.get(injuryPhase.getImagePath());
                Files.deleteIfExists(imagePath);
            }
            if (injuryPhase.getDrawingPath() != null) {
                Path drawingPath = Paths.get(injuryPhase.getDrawingPath());
                Files.deleteIfExists(drawingPath);
            }

            injuryPhaseRepository.delete(injuryPhase);
        } catch (Exception e) {
            return e.toString();
        }

        return "Injury phase successfully deleted";
    }

}
