package com.Test;

/**
 * 矩阵乘法
 * a点乘b，当矩阵a的列数x与矩阵b的行数y相等时可进行相乘
 * a乘b得到的新矩阵c，c的行数y等于a的行数，c的列数x等于b的列数
 * Created by Queena on 2017/8/19.
 */
public class MatrixMultiplicationDemo {
    public static int[][] matrix(int a[][], int b[][]) {
        //当a的列数与矩阵b的行数不相等时，不能进行点乘，返回null
        if (a[0].length != b.length)
            return null;
        //c矩阵的行数y，与列数x
        int y = a.length;
        int x = b[0].length;
        int c[][] = new int[y][x];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                //c矩阵的第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }
}
