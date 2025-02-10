package com.capital.capital_gains_calculator.service;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;

import java.util.List;

public interface CapitalGainsCalculator {
    List<TaxResult> processOperations(List<Operation> operations);
}
