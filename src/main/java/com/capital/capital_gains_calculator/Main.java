package com.capital.capital_gains_calculator;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.impl.CapitalGainsCalculatorImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static java.lang.System.*;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Main {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        out.println("Iniciando a aplicação...");
        out.println("Insira as operações no formato JSON e pressione Enter.");
        out.println("(Windows → Ctrl+Z + Enter para finalizar / Linux/macOS → Ctrl+D para finalizar)");


        String input = readInput(args);
        List<String> jsonBlocks = Arrays.stream(input.split("]\\s*\n"))
                .map(block -> block.trim() + "]")
                .toList();

        // 🔥 Define um pool de threads (ex: 4 threads simultâneas)
        try (ExecutorService executor = newFixedThreadPool(4)) {
            for (String block : jsonBlocks) {
                executor.submit(() -> processBlock(block));
            }
        }  // 🔥 Finaliza após concluir todas as execuções
    }

    /**
     * 🔥 Processa um bloco JSON como simulação independente.
     */
    static void processBlock(String jsonBlock) {
        try {
            List<Operation> operations = objectMapper.readValue(jsonBlock, new TypeReference<>() {});
            List<TaxResult> results = new CapitalGainsCalculatorImpl().processOperations(operations);

            // 🔥 Exibe cada simulação separadamente
            out.println(objectMapper.writeValueAsString(results));

        } catch (Exception e) {
            err.println("Erro ao processar JSON: " + e.getMessage());
        }
    }

    /**
     * 🔥 Lê a entrada do arquivo ou do stdin.
     */
    static String readInput(String[] args) {
        try (BufferedReader reader = args.length > 0
                ? new BufferedReader(new FileReader(args[0]))
                : new BufferedReader(new InputStreamReader(System.in))) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            err.println("Erro ao ler a entrada: " + e.getMessage());
            return "";
        }
    }
}
