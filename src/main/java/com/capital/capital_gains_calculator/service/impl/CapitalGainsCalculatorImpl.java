package com.capital.capital_gains_calculator.service.impl;

import com.capital.capital_gains_calculator.dto.Operation;
import com.capital.capital_gains_calculator.dto.TaxResult;
import com.capital.capital_gains_calculator.service.CapitalGainsCalculator;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@RequiredArgsConstructor
public class CapitalGainsCalculatorImpl implements CapitalGainsCalculator {

    @Override
    public List<TaxResult> processOperations(List<Operation> operations) {
        out.println("Starting processOperations");
        List<TaxResult> taxResults = new ArrayList<>();
        int totalShares = 0;
        double weightedAverage = 0.0;
        double[] accumulatedLoss = {0.0}; // Change accumulatedLoss to an array

        for (Operation op : operations) {
            out.println("Processing operation: " + op);
            weightedAverage = op.getOperation().equals("buy")
                    ? processBuyOperation(op, totalShares, weightedAverage)
                    : weightedAverage;

            double tax = op.getOperation().equals("sell")
                    ? processSellOperation(op, weightedAverage, accumulatedLoss) // Pass accumulatedLoss as an array
                    : 0.0;

            totalShares += op.getOperation().equals("buy") ? op.getQuantity() : -op.getQuantity();
            taxResults.add(new TaxResult(tax));
        }
        out.println("Finished processOperations");
        return taxResults;
    }

    private double processBuyOperation(Operation op, int totalShares, double weightedAverage) {
        out.println("Processing buy operation: " + op);
        return ((totalShares * weightedAverage) + (op.getQuantity() * op.getUnitCost())) / (totalShares + op.getQuantity());
    }

    private double processSellOperation(Operation op, double weightedAverage, double[] accumulatedLoss) {
        out.println("Processing sell operation " + op);
        double totalSaleValue = op.getUnitCost() * op.getQuantity();
        double totalCost = weightedAverage * op.getQuantity();
        double profit = totalSaleValue - totalCost;

        if (profit < 0) {
            accumulatedLoss[0] += Math.abs(profit);
            return 0.0;
        } else {
            double taxableProfit = Math.max(0, profit - accumulatedLoss[0]);
            accumulatedLoss[0] = Math.max(0, accumulatedLoss[0] - profit);
            return (totalSaleValue > 20000.0) ? taxableProfit * 0.2 : 0.0;
        }
    }

}