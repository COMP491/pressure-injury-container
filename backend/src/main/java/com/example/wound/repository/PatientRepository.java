package com.example.wound.repository;

import com.example.wound.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    PatientEntity findByBarcode(String barcode);

    PatientEntity findAllById(Long id);

}
