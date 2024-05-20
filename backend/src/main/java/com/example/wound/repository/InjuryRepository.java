package com.example.wound.repository;

import com.example.wound.entity.InjuryEntity;
import com.example.wound.rest.enums.InjuryLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InjuryRepository extends JpaRepository<InjuryEntity, Long> {

    InjuryEntity findAllById(Long id);

    @Query("SELECT DISTINCT w.location FROM injury w WHERE w.patient.id = :patientId")
    List<InjuryLocation> findDistinctLocationsByPatientId(@Param("patientId") Long patientId);
}
