package com.example.wound.repository;

import com.example.wound.entity.InjuryPhaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InjuryPhaseRepository extends JpaRepository<InjuryPhaseEntity, Long> {

    InjuryPhaseEntity findAllById(Long id);

}
