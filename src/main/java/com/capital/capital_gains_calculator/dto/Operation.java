package com.capital.capital_gains_calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("unit-cost")
    private double unitCost;

    @JsonProperty("quantity")
    private int quantity;

}
