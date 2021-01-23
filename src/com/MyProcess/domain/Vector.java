package com.MyProcess.domain;

public class Vector {
    double aVectorLen;
    double bVectorLen;

    public Vector() {
    }

    public Vector(double aVectorLen, double bVectorLen) {
        this.aVectorLen = aVectorLen;
        this.bVectorLen = bVectorLen;
    }

    public double getaVectorLen() {
        return aVectorLen;
    }

    public void setaVectorLen(double aVectorLen) {
        this.aVectorLen = aVectorLen;
    }

    public double getbVectorLen() {
        return bVectorLen;
    }

    public void setbVectorLen(double bVectorLen) {
        this.bVectorLen = bVectorLen;
    }
}
