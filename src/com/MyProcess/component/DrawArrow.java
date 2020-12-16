package com.MyProcess.component;

import com.MyProcess.domain.Coordinate;
import com.MyProcess.domain.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.ImageProducer;
import java.util.List;

// 传 index进来
public class DrawArrow {

    public void drawArrowOfAB(int index, double oX, double oY, double aX, double aY, double bX, double bY, List<Coordinate> arrsList) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // 读取图像到矩阵中
        Mat img = Imgcodecs.imread("src/images/src_process" + index + ".jpg");

        Scalar color = new Scalar(105, 105, 105);
        String strNum;

        // 画箭头
        Imgproc.arrowedLine(img, new Point(oX, oY), new Point(aX, aY), new Scalar(105, 105, 105), 1);
        Imgproc.arrowedLine(img, new Point(oX, oY), new Point(bX, bY), new Scalar(105, 105, 105), 1);


        // 输出图像
     /*   HighGui.imshow("箭头", img);
        HighGui.waitKey();*/

        Imgcodecs.imwrite("src/images/src_processArrow" + index + ".jpg", img);

    }

}
