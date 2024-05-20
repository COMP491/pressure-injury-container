package com.example.wound.controller;

import com.example.wound.dto.InjuryPhaseDTO;
import com.example.wound.dto.PatientDetailsDTO;
import com.example.wound.dto.PatientInjuriesDTO;
import com.example.wound.dto.PatientNamesDTO;
import com.example.wound.entity.InjuryEntity;
import com.example.wound.entity.InjuryPhaseEntity;
import com.example.wound.rest.requests.AddPatientRequest;
import com.example.wound.rest.requests.AddInjuryPhaseRequest;
import com.example.wound.rest.requests.AddInjuryRequest;
import com.example.wound.rest.requests.UpdateInjuryPhaseRequest;
import com.example.wound.service.PatientService;
import com.example.wound.service.InjuryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InjuryApiController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private InjuryService injuryService;



    @GetMapping("/get-patient-names")
    public ResponseEntity<List<PatientNamesDTO>> getPatientNames() {
        return ResponseEntity.ok().body(patientService.getPatientNames());
    }

    @GetMapping("/get-patient-details")
    public ResponseEntity<?> getPatientDetails(@RequestParam String barcode) {
        try {
            PatientDetailsDTO patient = patientService.getPatientDetails(barcode);
            return ResponseEntity.ok().body(patient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No patient found with barcode: " + barcode);
        }
    }

    @GetMapping("/get-patient-injuries-by-barcode")
    public ResponseEntity<List<PatientInjuriesDTO>> getPatientInjuriesByBarcode(@RequestParam String barcode) {
        return ResponseEntity.ok().body(patientService.getPatientInjuriesByBarcode(barcode));
    }

    @GetMapping("/get-injury-phases")
    public ResponseEntity<List<InjuryPhaseDTO>> getInjuryPhases(@RequestParam Long id) {
        return ResponseEntity.ok().body(injuryService.getInjuryPhases(id));
    }

    @PostMapping("/add-patient")
    public ResponseEntity<String> addPatient(@RequestBody AddPatientRequest request) {
        return ResponseEntity.ok().body(patientService.addPatient(request));
    }

    @PostMapping("/add-injury")
    public ResponseEntity<String> addInjury(@RequestBody AddInjuryRequest request) {
        return ResponseEntity.ok().body(injuryService.addInjury(request));
    }

    @PostMapping("/add-injury-phase")
    public ResponseEntity<String> addInjuryPhase(@RequestPart("image") MultipartFile image, @RequestPart(value = "drawingData", required = false) MultipartFile drawingData, @RequestPart("request") String requestString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddInjuryPhaseRequest request = objectMapper.readValue(requestString, AddInjuryPhaseRequest.class);
        return ResponseEntity.ok().body(injuryService.addInjuryPhase(image, drawingData, request));
    }

    @PutMapping("/update-injury-phase/{id}")
    public ResponseEntity<String> updateInjuryPhase(@PathVariable Long id, @RequestPart(value = "drawingData", required = false) MultipartFile drawingData, @RequestPart("request") String requestString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateInjuryPhaseRequest request = objectMapper.readValue(requestString, UpdateInjuryPhaseRequest.class);
        return ResponseEntity.ok().body(injuryService.updateInjuryPhase(id, drawingData, request));
    }

    @DeleteMapping("/delete-injury-phase/{id}")
    public ResponseEntity<String> deleteInjuryPhase(@PathVariable Long id) {
        return ResponseEntity.ok().body(injuryService.deleteInjuryPhase(id));
    }

    @DeleteMapping("/delete-injury/{id}")
    public ResponseEntity<String> deleteInjury(@PathVariable Long id) {
        return ResponseEntity.ok().body(injuryService.deleteInjury(id));
    }
}
