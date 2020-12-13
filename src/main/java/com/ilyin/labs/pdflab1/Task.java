package com.ilyin.labs.pdflab1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Task extends Application {

    private final static int taskIterationsCount = 10;

    private final static long executionConstant = 1;

    SquareMatrix matrix;

    private long getTimeForOneExecution() {
        return getTimeForSeveralExecutions(executionConstant) / executionConstant;
    }

    private long getTimeForOneExecutionInMillis() {
        return getTimeForOneExecution()/1000;
    }

    private void executeSeveralTimes(long executionsCount) {
        for (int i = 0; i < executionsCount; i++) {
            matrix.mainDiagonalElementsMultiple();
        }
    }

    private long getTimeForSeveralExecutions(long executionsCount) {
        long beginTime = System.nanoTime();
        executeSeveralTimes(executionsCount);
        return System.nanoTime() - beginTime;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Lab 3 Algorithms");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        xAxis.setLabel("Data volume (count of rows and columns in square matrix)");
        yAxis.setLabel("Algorithm execution time");

        lineChart.setTitle("Lab 1 Algorithms");

        XYChart.Series series = new XYChart.Series();
        series.setName("execution time graph");

        for (int i = 0; i < taskIterationsCount; i++) {

            int matrixSize = 1_000 * (i + 1);

            this.matrix = new SquareMatrix(matrixSize);

            matrix.randomFill();

            long currentExecutionTime = getTimeForOneExecutionInMillis();

            System.out.println("n = " + matrixSize + ", time = " + currentExecutionTime + "ms");

            series.getData().add(new XYChart.Data(matrixSize, currentExecutionTime));

        }

        Scene scene = new Scene(lineChart, 800, 600);

        lineChart.getData().add(series);

        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("kek");
    }
}
