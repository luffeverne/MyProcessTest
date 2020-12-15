package com.Test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class DrawArrow {
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //读取图像到矩阵中
        Mat img = Imgcodecs.imread("src/images/3.jpg");


        // 画箭头
        Imgproc.arrowedLine(img, new Point(50, 50), new Point(50, 100), new Scalar(0, 255, 0), 5);

        //输出图像
//        Imgcodecs.imwrite("src/images/circleonimg.jpg", img);
        HighGui.imshow("圆", img);
        HighGui.waitKey();

    }
}
