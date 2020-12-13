package com.ilyin.labs.pdflab4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Task extends Application {

    private static final int minValue = 0;
    private static final int maxValue = 100;
    private static final int numberConstant = 1000;
    private static final int taskIterationsCount = 10;
    private static final int repeatsCount = 5;
    private static final int executionsConstant = 100;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Lab 4 pdf");

        final NumberAxis bubbleSortXAxis = new NumberAxis();
        final NumberAxis bubbleSortYAxis = new NumberAxis();

        final NumberAxis shellSortXAxis = new NumberAxis();
        final NumberAxis shellSortYAxis = new NumberAxis();

        final LineChart<Number, Number> bubbleSortLineChart = new LineChart<Number, Number>(bubbleSortXAxis, bubbleSortYAxis);

        final LineChart<Number, Number> shellSortLineChart = new LineChart<Number, Number>(shellSortXAxis, shellSortYAxis);

        bubbleSortXAxis.setLabel("Data volume");
        bubbleSortYAxis.setLabel("Execution time");

        shellSortXAxis.setLabel("Data volume");
        shellSortYAxis.setLabel("Execution time");

        bubbleSortLineChart.setTitle("Bubble sort");
        shellSortLineChart.setTitle("Shell sort");

        XYChart.Series bubbleSortSeries = new XYChart.Series();
        bubbleSortSeries.setName("Bubble sort");

        XYChart.Series shellSortSeries = new XYChart.Series();
        shellSortSeries.setName("Shell sort");

        for (int i = 0; i < taskIterationsCount; ++i) {

            int arraysSize = (i + 1) * numberConstant;

            int[][] arrays = generateRandomArrayOfIntegerArrays(repeatsCount, arraysSize, minValue, maxValue);

            long currentBubbleSortExecutionTime = getMiddleBubbleSortExecutionTime(arrays);
            long currentShellSortExecutionTime = getMiddleShellSortExecutionTime(arrays);

            System.out.println("n = " + arraysSize + ", bubble sort time = " + currentBubbleSortExecutionTime + " ms, Shell sort time = " + currentShellSortExecutionTime + " ms");

            bubbleSortSeries.getData().add(new XYChart.Data(arraysSize, currentBubbleSortExecutionTime));
            shellSortSeries.getData().add(new XYChart.Data(arraysSize, currentShellSortExecutionTime));

        }

        Scene bubbleSortScene = new Scene(bubbleSortLineChart, 1280, 720);
        Scene shellSortScene = new Scene(shellSortLineChart, 1280,720);

        bubbleSortLineChart.getData().add(bubbleSortSeries);
        shellSortLineChart.getData().add(shellSortSeries);

        primaryStage.setScene(shellSortScene);
        primaryStage.show();

    }

    int[] bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
        return array;
    }

    int[] shellSort(int[] array) {
        int n = array.length;
        for (int step = n / 2; step > 0; step /= 2) {
            for (int i = step; i < n; i++) {
                for (int j = i - step; j >= 0 && array[j] > array[j + step]; j -= step) {
                    int x = array[j];
                    array[j] = array[j + step];
                    array[j + step] = x;
                }
            }
        }
        return array;
    }

    private void executeBubbleSortSeveralTimes(int executionsCount, int[] array) {
        int[] notSortedArray;
        for (int i = 0; i < executionsCount; i++) {
            notSortedArray = array;
            bubbleSort(notSortedArray);
        }
    }

    private void executeShellSortSeveralTimes(int executionsCount, int[] array) {
        int[] notSortedArray;
        for (int i = 0; i < executionsCount; i++) {
            notSortedArray = array;
            shellSort(notSortedArray);
        }
    }

    private long getExecutionTimeForBubbleSort(int[] array) {
        long beginTime = System.nanoTime();
        executeBubbleSortSeveralTimes(executionsConstant, array);
        return (System.nanoTime() - beginTime) / executionsConstant;
    }

    private long getExecutionTimeForShellSort(int[] array) {
        long beginTime = System.nanoTime();
        executeShellSortSeveralTimes(executionsConstant, array);
        return (System.nanoTime() - beginTime) / executionsConstant;
    }

    private int generateRandomInteger(int minValue, int maxValue) {
        return (int) (Math.random() * (maxValue - minValue) + minValue);
    }

    private int[] generateRandomIntegerArray(int size, int minValue, int maxValue) {
        int[] array = new int[size];
        for(int i = 0;i<size;++i) {
            array[i] = generateRandomInteger(minValue, maxValue);
        }
        return array;
    }

    private int[][] generateRandomArrayOfIntegerArrays(int n, int m, int minValue, int maxValue) {
        int[][] arrayOfArrays = new int[n][m];
        for (int[] array : arrayOfArrays) array = generateRandomIntegerArray(m, minValue, maxValue);
        return arrayOfArrays;
    }

    private long getMiddleBubbleSortExecutionTime(int[][] arrays) {
        long sumExecutionTime = 0;
        for (int[] array : arrays) {
            sumExecutionTime += getExecutionTimeForBubbleSort(array);
        }
        return sumExecutionTime / arrays.length;
    }

    private long getMiddleShellSortExecutionTime(int[][] arrays) {
        long sumExecutionTime = 0;
        for (int[] array : arrays) {
            sumExecutionTime += getExecutionTimeForShellSort(array);
        }
        return sumExecutionTime / arrays.length;
    }

}
