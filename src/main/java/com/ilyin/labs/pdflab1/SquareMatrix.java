package com.ilyin.labs.pdflab1;

public class SquareMatrix extends Matrix {
    public SquareMatrix(int n) {
        super(n, n);
    }

    public int mainDiagonalElementsMultiple() {
        int mainDiagonalElementsMultiple = 1;
        for (int i = 0; i < getMatrix().length; i++) {
            mainDiagonalElementsMultiple*=getMatrix()[i][i];
        }
        return mainDiagonalElementsMultiple;
    }
}
