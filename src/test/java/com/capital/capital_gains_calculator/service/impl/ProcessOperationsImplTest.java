package com.capital.capital_gains_calculator.service.impl;


import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.CapitalGainsCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProcessOperationsImplTest {

    private CapitalGainsCalculator calculator;
    private ProcessOperationsImpl processOperations;

    @BeforeEach
    void setUp() {
        calculator = Mockito.mock(CapitalGainsCalculator.class);
        processOperations = new ProcessOperationsImpl(calculator);
    }

    @Test
    void testExecute() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 100),
                new Operation("sell", 15.00, 50)
        );

        List<TaxResult> expectedResults = Arrays.asList(
                new TaxResult(0.0),
                new TaxResult(0.0)
        );

        when(calculator.processOperations(operations)).thenReturn(expectedResults);

        List<TaxResult> result = processOperations.execute(operations);

        assertEquals(expectedResults, result, "Os resultados devem ser iguais aos esperados");
        verify(calculator, times(1)).processOperations(operations);
    }
}