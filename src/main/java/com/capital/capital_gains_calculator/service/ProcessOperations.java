package com.capital.capital_gains_calculator.service;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;

import java.util.List;

public interface ProcessOperations {
    List<TaxResult> execute(List<Operation> operations);
}
