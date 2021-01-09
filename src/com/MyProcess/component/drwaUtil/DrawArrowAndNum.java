package com.MyProcess.component.drwaUtil;

import com.MyProcess.domain.Coordinate;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;

// 画箭头，改序号
// 传 index进来
public class DrawArrowAndNum {

    public void drawArrowOfAB(int index, double oX, double oY, double aX, double aY, double bX, double bY, double[][] matrix, List<Coordinate> arrList) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src= Imgcodecs.imread("src/images/src_process_none"+index+".jpg");

        Scalar color_gray = new Scalar(105, 105, 105);
        String strNum;

        // 画箭头
        Imgproc.arrowedLine(src, new Point(oX, oY), new Point(aX, aY), color_gray, 1);
        Imgproc.arrowedLine(src, new Point(oX, oY), new Point(bX, bY), color_gray, 1);

        // 重绘圆心
        for (int i = 0; i < matrix.length; i++) {
            strNum = "";
            strNum += (int)matrix[i][0]+","+(int)matrix[i][1];
            // 画坐标
            Imgproc.putText(src, strNum, new Point(arrList.get(i).getX(), arrList.get(i).getY()), 1, 1, color_gray);

        }

        // 输出图像
        Imgcodecs.imwrite("src/images/src_processArrowAndNewCoo" + index + ".jpg", src);

    }

}
