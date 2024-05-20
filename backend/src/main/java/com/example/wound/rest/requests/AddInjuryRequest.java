package com.example.wound.rest.requests;

import com.example.wound.rest.enums.InjuryLocation;
import com.example.wound.rest.enums.InjuryRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddInjuryRequest {

    private String barcode;

    private InjuryRegion region;

    private InjuryLocation location;

    private String date;

}
