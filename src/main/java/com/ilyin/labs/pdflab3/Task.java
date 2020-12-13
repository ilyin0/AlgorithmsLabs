package com.ilyin.labs.pdflab3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Task extends Application {

    private static final int minValue = 0;
    private static final int maxValue = 100;
    private static final int powerConstant = 10_000;
    private static final int taskIterationsCount = 10;
    private static final int repeatsCount = 5;
    private static final int executionsConstant = 100;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lab 3 pdf");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        xAxis.setLabel("Data volume (power of number)");
        yAxis.setLabel("Execution time");

        lineChart.setTitle("Recursive power");

        XYChart.Series series = new XYChart.Series();
        series.setName("Execution time graph");

        for (int i = 0; i < taskIterationsCount; i++) {

            double[] numbers = new double[repeatsCount];
            int power = (i + 1) * powerConstant;

            for (int j = 0; j < numbers.length; j++) {
                numbers[j] = generateRandomDouble(minValue, maxValue);
            }

            long currentExecutionTime = getMiddleExecutionTimeInMillis(numbers, power);

            System.out.println("n = " + power + ", time = " + currentExecutionTime + " ms");

            series.getData().add(new XYChart.Data(power, currentExecutionTime));
        }

        Scene scene = new Scene(lineChart, 1280, 720);

        lineChart.getData().add(series);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private long getMiddleExecutionTimeInMillis(double[]numbers, int power) {
        return getMiddleExecutionTime(numbers, power)/1000;
    }

    private long getMiddleExecutionTime(double[]numbers, int power) {
        long sumExecutionTime = 0;
        for (double number : numbers) {
            sumExecutionTime += getExecutionTimeForPower(number, power);
        }
       return sumExecutionTime/numbers.length;
    }

    private double power(double number, int power) {
        return power > 1 ? power(number, power - 1) * number : number;
    }

    private double generateRandomDouble(double minValue, double maxValue) {
        return Math.random() * (maxValue - minValue) + minValue;
    }

    private int generateRandomInteger(int minValue, int maxValue) {
        return (int) generateRandomDouble(minValue, maxValue);
    }

    private long getExecutionTimeForPower(double number, int power) {
        long beginTime = System.nanoTime();
        executePowerSeveralTimes(executionsConstant, number, power);
        return (System.nanoTime() - beginTime)/executionsConstant;
    }

    private void executePowerSeveralTimes(int executionsCount, double number, int power) {
        for (int i = 0; i < executionsCount; i++) {
            power(number, power);
        }
    }

}
