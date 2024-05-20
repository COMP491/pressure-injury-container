package com.example.wound.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "patient")
@Getter
@Setter
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String barcode;

    private Integer age;

    private String gender;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<InjuryEntity> injuries = new ArrayList<>();

}
