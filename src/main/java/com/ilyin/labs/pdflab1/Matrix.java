package com.ilyin.labs.pdflab1;

public class Matrix {
    private int[][] matrix;

    private final int maxValue = 10_000;
    private final int minValue = 0;
    private final int defaultValue = 10;

    public static final int minSize = 5;
    public static final int maxSize = 10_000;

    public Matrix() {
        this.matrix = new int[defaultValue][defaultValue];
    }

    public Matrix(int n, int m) {
        this.matrix = new int[n][m];
    }

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void randomFill() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = (int) ((Math.random() * 480) + 20);
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }
}
