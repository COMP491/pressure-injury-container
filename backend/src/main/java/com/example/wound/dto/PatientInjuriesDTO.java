package com.example.wound.dto;

import com.example.wound.entity.InjuryEntity;
import com.example.wound.rest.enums.InjuryLocation;
import com.example.wound.rest.enums.InjuryRegion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatientInjuriesDTO {

    private Long id;

    private InjuryRegion region;

    private InjuryLocation location;

    private String date;
}
