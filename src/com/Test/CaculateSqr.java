package com.Test;

public class CaculateSqr {
    public static void main(String[] args) {
        double[][] arr = new double[3][3];

        double oX, oY, aX, aY, bX, bY;

        // O: 337.0, 337.2
        // A: 389.0, 337.0
        // B: 337.0, 373.5
        oX = 337.0;
        oY = 337.2;

        aX = 389.0;
        aY = 337.0;

        bX = 337.0;
        bY = 373.5;


        System.out.println(Math.sqrt((aX - oX) * (aX - oX) + (aY - oY) * (aY - oY)));
        System.out.println(Math.sqrt((bX - oX) * (bX - oX) + (bY - oY) * (bY - oY)));
    }
}
