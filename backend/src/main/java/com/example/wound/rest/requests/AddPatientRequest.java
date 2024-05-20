package com.example.wound.rest.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPatientRequest {

    private String name;

    private String barcode;

    private Integer age;

    private String gender;

}
