package com.MyProcess.component.matrixUtil;


import com.MyProcess.component.toolUtil.FormatDouble;

public class TwoMatrixMultiply{

    // 多维 乘 多维
    public static double[][] Multiply(double[][] a, double[][] b) {
        int row = a.length;
        int col = b[0].length;

        double[][] res = new double[row][col];


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // c矩阵第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++) {
                    res[i][j] += a[i][k] * b[k][j];
//                    System.out.print(res[i][j] + " ");
                }
//                System.out.print("\n");
            }
        }
        return res;
    }

    // 一维 乘 多维
    public static double[] Multiply1(double[] a, double[][] b) {
        int row = a.length;
        int col = b[0].length;

        double[]res = new double [col];



        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < col; j++) {
                // c矩阵第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++) {
//                    res[i][j] += a[i][k] * b[k][j];


                    res[j] += a[k] * b[k][j];

//                    System.out.println(a[k] + " " + b[k][j] + " " +  res[j] + " ");
                }
//                System.out.print("\n");
            }
        }

       /* for(double x : res) {
            System.out.println(x);
        }*/
        return res;
    }


/*
    public static void main(String[] args) {
 *//*       double[][] a = {
                {1, 2, 3},
                {1, 2, 3}
        };
*//*
        double[] a1 = { 1, 2, 3};

        double[][] b = {
                {1, 2},
                {1, 2},
                {1, 2}
        };

//        Multiply(a, b);
        Multiply1(a1, b);
    }*/
 }
