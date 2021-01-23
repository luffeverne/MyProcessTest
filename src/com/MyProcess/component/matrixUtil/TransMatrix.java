package com.MyProcess.component.matrixUtil;

import com.MyProcess.component.toolUtil.FormatDouble;

public class TransMatrix {
    public static double[][] getTransMatrix(double[][] data) {
        double[][] tMatrix = new double[data[0].length][data.length];
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data.length; j++) {
                tMatrix[i][j] = data[j][i];
            }
        }

        return tMatrix;
    }

    // 一维转
    public static double[][] getTransMatrix1(double[] data) {
        double[][] tMatrix = new double[data.length][1];
        for (int i = 0; i < data.length; i++) {
            tMatrix[i][0] = data[i];
        }
        return tMatrix;
    }
}
