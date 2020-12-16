package com.MyProcess.component;

public class OrderTwoInverseMatrix {

    public static double[][] getMartrixResult(double[][] data) {

        double[][] newdata = new double[data.length][data.length];
        // 矩阵的行列式
        double A = data[0][0] * data[1][1] - data[0][1] * data[1][0];

//        System.out.println(A);
        A = 1.0 / A;
//        System.out.println(A);

        newdata[0][0] = A * data[1][1];
        newdata[1][1] = A * data[0][0];
        newdata[0][1] = (-1) * A * data[0][1];
        newdata[1][0] = (-1) * A * data[1][0];

     /*   for (int i = 0; i < newdata.length; i++) {
            for (int j = 0; j < newdata[0].length; j++) {
                System.out.print(newdata[i][j] + " ");
            }
            System.out.println(" ");
        }*/
        return newdata;
    }

    /*public static void main(String[] args) {
        double[][] data = {
                {108017.35196675178, -88919.27925401441 },
                {-88919.27925401441, 73242.42875782121}
        };
        getMartrixResult(data);
    }*/
}
