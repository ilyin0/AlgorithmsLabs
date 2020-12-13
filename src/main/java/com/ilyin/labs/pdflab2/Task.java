package com.ilyin.labs.pdflab2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Arrays;

public class Task extends Application {

    private static final int minValue = 0;
    private static final int maxValue = 100;
    private static final int arraySizeConstant = 1_000_000;
    private static final int taskIterationsCount = 10;
    private static final int repeatsCount = 5;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lab 2 PDF");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        xAxis.setLabel("Data volume (array length)");
        yAxis.setLabel("Execution time");

        lineChart.setTitle("Interpolation find");

        XYChart.Series series = new XYChart.Series();
        series.setName("Execution time graph");

        for (int i = 0; i < taskIterationsCount; i++) {
            int arraySize = (i + 1) * arraySizeConstant;

            int[][]arrays = new int[repeatsCount][arraySize];
            int[]toFindElements = new int[repeatsCount];

            for (int j = 0; j < arrays.length; j++) {
                arrays[j] = getRandomFilledIntegerArray(arraySize, minValue, maxValue);
                toFindElements[j] = generateRandomInteger(minValue, maxValue);
            }

            long currentExecutionTime = getMiddleExecutionTimeForOneInterpolationSearchInSeveralArrays(arrays, toFindElements);

            System.out.println("n = " + arraySize + ", time = " + currentExecutionTime + " ms");

            series.getData().add(new XYChart.Data(arraySize, currentExecutionTime));
        }

        Scene scene = new Scene(lineChart, 1280, 720);

        lineChart.getData().add(series);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private long getMiddleValueOfLongArray(long[]array) {
        int arrayElementsSum = 0;
        for (long value : array) {
            arrayElementsSum += value;
        }
        return arrayElementsSum;
    }

    private long getMiddleExecutionTimeForOneInterpolationSearchInSeveralArrays(int[][] arrays, int[] toFindElements) {
        long[] executionTimes = new long[arrays.length];
        for (int i = 0; i < executionTimes.length; i++) {
            executionTimes[i] = getExecutionTimeForOneInterpolationSearch(arrays[i], toFindElements[i]);
        }
        return getMiddleValueOfLongArray(executionTimes);
    }

    public int interpolationSearch(int[] array, int toFind) {
        Arrays.sort(array);
        return interpolationSearchForSortedArray(array, toFind);
    }

    private long getExecutionTimeForOneInterpolationSearch(int[] array, int toFind) {
        Arrays.sort(array);
        return getTimeForOneInterpolationSearchForSortedArray(array, toFind);
    }

    private long getTimeForOneInterpolationSearchForSortedArray(int[] sortedArray, int toFind) {
        long beginTime = System.nanoTime();
        interpolationSearchForSortedArray(sortedArray, toFind);
        return System.nanoTime() - beginTime;
    }

    public int interpolationSearchForSortedArray(int[] sortedArray, int toFind) {
        int mid;
        int low = 0;
        int high = sortedArray.length - 1;

        while (sortedArray[low] < toFind && sortedArray[high] > toFind) {
            if (sortedArray[high] == sortedArray[low]) break;

            mid = low + ((toFind - sortedArray[low]) * (high - low)) / (sortedArray[high] - sortedArray[low]);

            if (sortedArray[mid] < toFind)
                low = mid + 1;
            else if (sortedArray[mid] > toFind)
                high = mid - 1;
            else
                return mid;
        }

        if (sortedArray[low] == toFind)
            return low;
        if (sortedArray[high] == toFind)
            return high;

        return -1;
    }

    public int[] randomFillIntegerArray(int[] array, int minValue, int maxValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = generateRandomInteger(minValue, maxValue);
        }
        return array;
    }

    public int[] generateArray(int size) {
        return new int[size];
    }

    public int[] getRandomFilledIntegerArray(int size, int minValue, int maxValue) {
        return randomFillIntegerArray(generateArray(size), minValue, maxValue);
    }

    public int generateRandomInteger(int minValue, int maxValue) {
        return (int) (Math.random() * (maxValue - minValue) + minValue);
    }
}
