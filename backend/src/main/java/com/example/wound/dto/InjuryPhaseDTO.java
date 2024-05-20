package com.example.wound.dto;

import com.example.wound.rest.PhotoDate;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InjuryPhaseDTO {

    private Long id;

    @Embedded
    private PhotoDate photoDate;

    private String degree;

    private Double length;

    private Double width;

    private String notes;

    @ElementCollection
    private List<Boolean> conditionsTicked;

    private byte[] image; // Add this field

    private byte[] drawingData;
}
