package com.MyProcess.component;


public class TwoMatrixMultiply{
    
    public double[][] Multiply(double[][] a, double[][] b) {
        int row = a.length;
        int col = b[0].length;

        double[][] res = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // c矩阵第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }

   /* public static void main(String[] args) {
        double[][] a = {
                {1, 2, 3},
                {1, 2, 3}
        };

        double[][] b = {
                {1, 2},
                {1, 2},
                {1, 2}
        };

        Multiply(a, b);
    }*/
 }
