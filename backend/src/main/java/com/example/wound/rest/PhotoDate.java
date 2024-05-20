package com.example.wound.rest;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PhotoDate {

    private int day;

    private int month;

    private int year;

}
