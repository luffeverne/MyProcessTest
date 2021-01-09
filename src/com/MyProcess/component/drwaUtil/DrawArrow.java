package com.MyProcess.component.drwaUtil;

import com.MyProcess.domain.Coordinate;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrawArrow {
    public void drawArrowOfAB(int index, double oX, double oY, double aX, double aY, double bX, double bY) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat img = Imgcodecs.imread("src/images/src_process"+index+".jpg");

        Scalar color = new Scalar(105, 105, 105);

        // 画箭头
        Imgproc.arrowedLine(img, new Point(oX, oY), new Point(aX, aY), color, 1);
        Imgproc.arrowedLine(img, new Point(oX, oY), new Point(bX, bY), color, 1);


        // 输出图像
        Imgcodecs.imwrite("src/images/src_processArrowAndNewCoo" + index + ".jpg", img);

    }

}
