package com.MyProcess.component.drwaUtil;

/*
 * @params index 图片坐标
 * @params
 * */

import com.MyProcess.domain.Coordinate;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class DrawCoordinate {

    public void drawCoordinateOfNew(int index, double[][] matrix, List<Coordinate> arrList) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        Mat img = Imgcodecs.imread("src/images/src_process" + index + ".jpg");
        Mat img = Imgcodecs.imread("src/images/ImageTest.png");
        String str;
        Scalar color = new Scalar(255, 255, 255);

        double[][] tList = new double[arrList.size()][2];

        for (int i = 0; i < arrList.size(); i++) {
            tList[i][0] = arrList.get(i).getX();
            tList[i][1] = arrList.get(i).getY();
        }

        for (int i = 0; i < matrix.length; i++) {
            str = "(" + matrix[i][0] + "," + matrix[i][1] + ")";
            System.out.println(str);

            System.out.println("X:"+arrList.get(i).getX()+", Y:"+arrList.get(i).getY());

            // 绘制坐标
            Imgproc.putText(img, str, new Point(tList[i][0], tList[i][1]), 1, 1, color);
            // 将坐标输出到图片
            Imgcodecs.imwrite("src_processArrowAndNewCoo"+index+".jpg", img);

        }
    }
}
