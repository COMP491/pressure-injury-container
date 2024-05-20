package com.example.wound.entity;

import com.example.wound.rest.enums.InjuryLocation;
import com.example.wound.rest.enums.InjuryRegion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "injury")
@Getter
@Setter
public class InjuryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private InjuryRegion region;

    @Enumerated(value = EnumType.STRING)
    private InjuryLocation location;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    private String date;

    @OneToMany(mappedBy = "injury", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InjuryPhaseEntity> injuryPhases = new ArrayList<>();

}
