package com.capital.capital_gains_calculator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.CapitalGainsCalculator;
import com.capital.capital_gains_calculator.service.impl.CapitalGainsCalculatorImpl;

import java.io.*;
import java.util.List;

import static java.lang.System.*;

public class Main {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        CapitalGainsCalculator calculator = new CapitalGainsCalculatorImpl();

        try {
            List<Operation> operations = loadOperations(args);
            if (operations == null || operations.isEmpty()) {
                err.println("Nenhuma operação válida encontrada.");
                return;
            }

            List<TaxResult> results = calculator.processOperations(operations);
            out.println(objectMapper.writeValueAsString(results));

        } catch (Exception e) {
            err.println("Erro ao processar operações: " + e.getMessage());
        }
    }

    /**
     * Carrega as operações de um arquivo JSON ou do stdin.
     */
    private static List<Operation> loadOperations(String[] args) throws IOException {
        return (args.length > 0) ? readFromFile(args[0]) : readFromStdin();
    }

    /**
     * Lê operações de um arquivo JSON.
     */
    private static List<Operation> readFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        return (!file.exists()) ? logFileNotFound(filePath) : readFile(file);
    }

    private static List<Operation> logFileNotFound(String filePath) {
        err.println("Arquivo não encontrado: " + filePath);
        return null;
    }

    private static List<Operation> readFile(File file) throws IOException {
        out.println("Lendo operações do arquivo: " + file.getPath());
        return objectMapper.readValue(file, new TypeReference<List<Operation>>() {});
    }

    /**
     * Lê operações da entrada padrão (stdin).
     */
    private static List<Operation> readFromStdin() throws IOException {
        out.println("Insira as operações no formato JSON e pressione Enter " +
                "(Windows → Ctrl+Z + Enter para finalizar/ Linux/macOS → Ctrl+D para finalizar):");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder input = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            input.append(line);
        }
        String inputData = input.toString();
        out.println("Entrada Recebida: " + inputData);

        return objectMapper.readValue(inputData, new TypeReference<List<Operation>>() {});
    }
}
