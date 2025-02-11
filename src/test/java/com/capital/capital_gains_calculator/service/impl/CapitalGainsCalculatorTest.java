package com.capital.capital_gains_calculator.service.impl;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.CapitalGainsCalculator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapitalGainsCalculatorTest {

    private final CapitalGainsCalculator calculator = new CapitalGainsCalculatorImpl();

    @Test
    void testCase1_SemImposto() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 20.00, 5000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.0, result.get(1).getTax(), "O imposto deve ser 0.0");
    }

    @Test
    void testCase2_ComImposto() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 30.00, 5000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(3000.0, result.get(1).getTax(), "O imposto deve ser 3000.0");
    }

    @Test
    void testCase3_LossCarryForward() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 5.00, 5000),
                new Operation("sell", 30.00, 5000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.0, result.get(1).getTax(), "Perda acumulada, imposto deve ser 0.0");
        assertEquals(5000.0, result.get(2).getTax(), "Após recuperação da perda, imposto deve ser 5000.0");
    }

    @Test
    void testCase4_ComprarEFecharPosicao() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 20.00, 10000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(10000.0, result.get(1).getTax(), "Imposto deve ser 10000.0");
    }
}