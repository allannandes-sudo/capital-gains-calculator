package com.capital.capital_gains_calculator;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.impl.CapitalGainsCalculatorImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MainTest {

    private ObjectMapper objectMapper;
    private CapitalGainsCalculatorImpl calculator;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        calculator = Mockito.mock(CapitalGainsCalculatorImpl.class);
    }

    @Test
    void testProcessBlock() throws Exception {
        String jsonBlock = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100}]";
        List<Operation> operations = objectMapper.readValue(jsonBlock, new TypeReference<>() {});
        List<TaxResult> expectedResults = List.of(new TaxResult(0.0));

        when(calculator.processOperations(operations)).thenReturn(expectedResults);

        ByteArrayInputStream in = new ByteArrayInputStream(jsonBlock.getBytes());
        System.setIn(in);

        Main.processBlock(jsonBlock);

        assertEquals(expectedResults, calculator.processOperations(operations));


        System.setIn(System.in);
    }

    @Test
    void testReadInput() {
        String input = "test input";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        String result = Main.readInput(new String[]{});
        assertEquals(input, result);

        System.setIn(System.in);
    }
}