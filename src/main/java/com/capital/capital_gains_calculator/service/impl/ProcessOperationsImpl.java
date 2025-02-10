package com.capital.capital_gains_calculator.service.impl;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.CapitalGainsCalculator;
import com.capital.capital_gains_calculator.service.ProcessOperations;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProcessOperationsImpl implements ProcessOperations {

    private final CapitalGainsCalculator calculator;

    @Override
    public List<TaxResult> execute(List<Operation> operations) {
        return calculator.processOperations(operations);
    }
}
