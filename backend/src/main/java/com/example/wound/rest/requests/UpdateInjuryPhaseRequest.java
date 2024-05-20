package com.example.wound.rest.requests;

import com.example.wound.rest.PhotoDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInjuryPhaseRequest {

    private Long id;

    private String photoId;

    private Long injuryId;

    private PhotoDate photoDate;

    private String degree;

    private Double length;

    private Double width;

    private String notes;

    private List<Boolean> conditionsTicked;
}
