package com.capital.capital_gains_calculator.service.impl;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.CapitalGainsCalculator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CapitalGainsCalculatorTest {

    private final CapitalGainsCalculator calculator = new CapitalGainsCalculatorImpl();

    @Test
    void testCase1_SemImposto() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 100),
                new Operation("sell", 15.00, 50),
                new Operation("sell", 15.00, 50)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.0, result.get(0).getTax(), "O imposto deve ser 0.0");
        assertEquals(0.0, result.get(1).getTax(), "O imposto deve ser 0.0");
        assertEquals(0.0, result.get(2).getTax(), "O imposto deve ser 0.0");
    }

    @Test
    void testCase2_ComImposto() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 20.00, 5000),
                new Operation("sell", 5.00, 5000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.00, result.get(0).getTax(), "O imposto deve ser 0.00");
        assertEquals(10000.00, result.get(1).getTax(), "O imposto deve ser 10000.00");
        assertEquals(0.00, result.get(2).getTax(), "O imposto deve ser 0.00");
    }

    @Test
    void testCase1_Case2_DoubleList() {
        CapitalGainsCalculator calculator = new CapitalGainsCalculatorImpl();

        // 🔥 Primeira simulação
        List<Operation> operations1 = Arrays.asList(
                new Operation("buy", 10.00, 100),
                new Operation("sell", 15.00, 50),
                new Operation("sell", 15.00, 50)
        );

        List<TaxResult> result1 = calculator.processOperations(operations1);

        assertEquals(0.00, result1.get(0).getTax(), "Simulação 1 - O imposto deve ser 0.00");
        assertEquals(0.00, result1.get(1).getTax(), "Simulação 1 - O imposto deve ser 0.00");
        assertEquals(0.00, result1.get(2).getTax(), "Simulação 1 - O imposto deve ser 0.00");

        // 🔥 Segunda simulação (independente da primeira)
        List<Operation> operations2 = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 20.00, 5000),
                new Operation("sell", 5.00, 5000)
        );

        List<TaxResult> result2 = calculator.processOperations(operations2);

        assertEquals(0.00, result2.get(0).getTax(), "Simulação 2 - O imposto deve ser 0.00");
        assertEquals(10000.00, result2.get(1).getTax(), "Simulação 2 - O imposto deve ser 10000.00");
        assertEquals(0.00, result2.get(2).getTax(), "Simulação 2 - O imposto deve ser 0.00");
    }


    @Test
    void testCase3_LossCarryForward() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 5.00, 5000),
                new Operation("sell", 20.00, 3000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.0, result.get(0).getTax(), "Perda acumulada, imposto deve ser 0.0");
        assertEquals(0.0, result.get(1).getTax(), "Perda acumulada, imposto deve ser 0.0");
        assertEquals(1000.0, result.get(2).getTax(), "Após recuperação da perda, imposto deve ser 5000.0");
    }

    @Test
    void testCase4_ComprarEFecharPosicao() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("buy", 25.00, 5000),
                new Operation("sell", 15.00, 10000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.00, result.get(0).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(1).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(2).getTax(), "O imposto deve ser 0.00");
    }

    @Test
    void testCase5() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("buy", 25.00, 5000),
                new Operation("sell", 15.00, 10000),
                new Operation("sell", 25.00, 5000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.00, result.get(0).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(1).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(2).getTax(), "O imposto deve ser 0.00");
        assertEquals(10000.00, result.get(3).getTax(), "O imposto deve ser 10000.00");
    }

    @Test
    void testCase6() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 2.00, 5000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 25.00, 1000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.00, result.get(0).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(1).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(2).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(3).getTax(), "O imposto deve ser 0.00");
        assertEquals(3000.00, result.get(4).getTax(), "O imposto deve ser 3000.00");
    }

    @Test
    void testCase7() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 2.00, 5000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 20.00, 2000),
                new Operation("sell", 25.00, 1000),
                new Operation("buy", 20.00, 10000),
                new Operation("sell", 15.00, 5000),
                new Operation("sell", 30.00, 4350),
                new Operation("sell", 30.00, 650)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.00, result.get(0).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(1).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(2).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(3).getTax(), "O imposto deve ser 0.00");
        assertEquals(3000.00, result.get(4).getTax(), "O imposto deve ser 3000.00");
        assertEquals(0.00, result.get(5).getTax(), "O imposto deve ser 0.00");
        assertEquals(0.00, result.get(6).getTax(), "O imposto deve ser 0.00");
        assertEquals(3700.00, result.get(7).getTax(), "O imposto deve ser 3700.00");
        assertEquals(0.00, result.get(8).getTax(), "O imposto deve ser 0.00");
    }

    @Test
    void testCase8() {
        List<Operation> operations = Arrays.asList(
                new Operation("buy", 10.00, 10000),
                new Operation("sell", 50.00, 10000),
                new Operation("buy", 20.00, 10000),
                new Operation("sell", 50.00, 10000)
        );

        List<TaxResult> result = calculator.processOperations(operations);

        assertEquals(0.00, result.get(0).getTax(), "O imposto deve ser 0.00");
        assertEquals(80000.00, result.get(1).getTax(), "O imposto deve ser 80000.00");
        assertEquals(0.00, result.get(2).getTax(), "O imposto deve ser 0.00");
        assertEquals(60000.00, result.get(3).getTax(), "O imposto deve ser 60000.00");
    }
}