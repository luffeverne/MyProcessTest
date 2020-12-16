package com.MyProcess.component;

import com.MyProcess.domain.Coordinate;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class FindCenterOfCircle {
    //静态代码加载动态链接库
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public List<Coordinate> handlePicture(String path, int index) {
        List<Coordinate> coordinateList = new ArrayList<>();

//        String[] resCoordinates = new String[100];
        String strNum ="";

        Mat src = Imgcodecs.imread(path);
        if (src == null) {
            System.out.println("error");
            return null;
        }
/*
        HighGui.imshow("原图", src);
        HighGui.waitKey();*/

        Mat dst = new Mat();

        //将图像变灰
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);

        //均值模糊
        Imgproc.blur(dst, dst, new Size(3, 3));

        // 将固定级别的阈值应用于每个数组元素。
        Imgproc.threshold(dst, dst, 128, 255, Imgproc.THRESH_BINARY_INV);

        // 查找二进制图像中的轮廓

        List<MatOfPoint> list = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        // 查找轮廓，返回值放在list
        Imgproc.findContours(dst, list, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        List<MatOfPoint> approxCurvelList = new ArrayList<MatOfPoint>();
        List<Rect> rectList = new ArrayList<>();
        List<Point> centerList = new ArrayList<>();
        List<Integer> radiusList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            Point center = new Point();
            float[] radius = new float[10];

            //approxPolyDP用一条具有较少顶点的曲线或多边形最大化接近一条曲线或多边形，以使它们之间的距离小于或等于指定的精度。
            Imgproc.approxPolyDP(new MatOfPoint2f(list.get(i).toArray()), approxCurve, 3, true);
            approxCurvelList.add(new MatOfPoint(approxCurve.toArray()));

            // 计算点集或灰度图像的非零像素的有边界矩形，返回最小的垂直边界矩形
            Rect boundRect = Imgproc.boundingRect(approxCurve);
            rectList.add(boundRect);

            // 查找包含2D点集的最小面积的圆，使用迭代算法找到2D点集的最小封闭圆
            Imgproc.minEnclosingCircle(approxCurve, center, radius);




            centerList.add(center);
            radiusList.add((int)radius[0]);
        }

        Scalar color =  new Scalar(255, 255, 255);
        Scalar color2 = new Scalar(255, 144 , 30);

        for (int i = 0; i < list.size(); i++) {
            // 绘制圆心
            Imgproc.circle(src, centerList.get(i), 1, color, 1, Imgproc.LINE_AA);
//            Imgproc.drawMarker(src, centerList.get(i), color);


            // 输出圆心点集
//            System.out.println(centerList.get(i));

            strNum ="";
            strNum = strNum+i;

            String centerX = String.format("%.1f", centerList.get(i).x);
            String centerY = String.format("%.1f", centerList.get(i).y);

            Coordinate coordinate = new Coordinate(Double.parseDouble(centerX), Double.parseDouble(centerY));
            coordinateList.add(coordinate);

    /*        String tStr = ("("+centerX+","+centerY+")");
            resCoordinates[i] = strNum+" "+tStr;*/
//            System.out.println("("+centerX+", "+centerY+")");


            // 绘制坐标
            Imgproc.putText(src, strNum, centerList.get(i), 1, 1, color2);

        }

       /* HighGui.imshow("边界框和圆", src);
        HighGui.waitKey();*/
        Imgcodecs.imwrite("src/images/src_process"+index+".jpg", src);

        return coordinateList;
    }

}
