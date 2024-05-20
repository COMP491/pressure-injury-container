package com.example.wound.entity;

import com.example.wound.rest.PhotoDate;
import com.example.wound.rest.enums.InjuryRegion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "injury_phase")
@Getter
@Setter
public class InjuryPhaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoId;

    @ManyToOne
    @JoinColumn(name = "injury_id")
    private InjuryEntity injury;

    @Embedded
    private PhotoDate photoDate;

    private String degree;

    private Double length;

    private Double width;

    @Column(columnDefinition = "LONGTEXT")
    private String notes;

    @ElementCollection
    private List<Boolean> conditionsTicked;

    private String imagePath;

    private String drawingPath;
}

