package com.Test.OpenCVDemo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FindBorderDemo {

    //静态代码块加载动态链接库
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        /*
         * IMREAD_UNCHANGED = -1 ：不进行转化，比如保存为了16位的图片，读取出来仍然为16位。
         * IMREAD_GRAYSCALE = 0 ：进行转化为灰度图，比如保存为了16位的图片，读取出来为8位，类型为CV_8UC1。
         * IMREAD_COLOR = 1 ：进行转化为三通道图像。
         * IMREAD_ANYDEPTH = 2 ：如果图像深度为16位则读出为16位，32位则读出为32位，其余的转化为8位。
         * IMREAD_ANYCOLOR = 4 ：图像以任何可能的颜色格式读取
         * IMREAD_LOAD_GDAL = 8 ：使用GDAL驱动读取文件，GDAL(Geospatial Data Abstraction
         * Library)是一个在X/MIT许可协议下的开源栅格空间数据转换库。它利用抽象数据模型来表达所支持的各种文件格式。
         *	它还有一系列命令行工具来进行数据转换和处理。
         */

//        Mat src = Imgcodecs.imread("C:\\Users\\Luffe\\Pictures\\test\\circles\\ImageTest.tif");//待匹配图片
        Mat src = Imgcodecs.imread("C:\\Users\\Luffe\\Pictures\\test\\circles\\1.jpg");//待匹配图片
        Imgproc.resize(src, src, new Size(src.cols()/2,src.rows()/2));

        HighGui.imshow("原图", src);
        HighGui.waitKey();

        //图片灰度化 https://blog.csdn.net/ren365880/article/details/103869207
        Mat gary = new Mat();
        Imgproc.cvtColor(src, gary, Imgproc.COLOR_BGR2GRAY);

        //图像边缘处理 https://blog.csdn.net/ren365880/article/details/103938232
        Mat edges = new Mat();
        Imgproc.Canny(gary, edges, 200, 500, 3, false);

        //发现轮廓
        List<MatOfPoint> list = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();

        /*
         * 查找二进制图像中的轮廓。
         * 该函数使用CITE算法：Suzuki85从二进制图像检索轮廓。轮廓是用于形状分析以及对象检测和识别的有用工具。
         * @param image Source，一个8位单通道图像。非零像素被视为1。零像素保持为0，因此图像被视为binary。您可以使用＃compare，＃inRange，
         * ＃threshold，＃adaptiveThreshold，＃Canny等创建灰度或彩色以外的二进制图像。如果模式等于#RETR_CCOMP或#
         * RETR_FLOODFILL，则输入也可以是32位的整数图像（CV_32SC1）。
         * @param轮廓检测到的轮廓。每个轮廓都存储为点的向量。
         * @param层次结构可选的输出向量 包含有关图像拓扑的信息。它具有与轮廓数量一样多的元素。对于每个第i个轮廓轮廓[i]，元素等级[i] [0]，等级[i] [1]，等级[i]
         * [2]和等级[i][3]均设置为0-在相同的层次级别上，基于下一个和上一个轮廓的轮廓的索引，分别是第一个子轮廓和父轮廓。如果对于轮廓i，没有下一个，上一个，父级或嵌套的轮廓
         * ，则hierarchy [i]的相应元素将为负。
         * @param模式轮廓检索模式，请参见#RetrievalModes
         * RETR_EXTERNAL = 0, 只检测最外围轮廓，包含在外围轮廓内的内围轮廓被忽略；
         * RETR_LIST = 1,检测所有的轮廓，包括内围、外围轮廓，但是检测到的轮廓不建立等级关系，彼此之间独立，没有等级关系，这就意味着这个检索模式下不存在父轮廓或内嵌轮廓
         * RETR_CCOMP = 2,检测所有的轮廓，但所有轮廓只建立两个等级关系，外围为顶层，若外围内的内围轮廓还包含了其他的轮廓信息，则内围内的所有轮廓均归属于顶层；
         * RETR_TREE = 3,检测所有轮廓，所有轮廓建立一个等级树结构。外层轮廓包含内层轮廓，内层轮廓还可以继续包含内嵌轮廓
         * RETR_FLOODFILL = 4; 官方没定义 使用此参数需要把输入源转为CV_32SC1
         * Mat dst = new Mat();
         * edges.convertTo(dst,CvType.CV_32SC1);
         * @param方法轮廓近似方法，请参阅#ContourApproximationModes轮廓是从图像ROI中提取的，然后应在整个图像上下文中进行分析。
         * CHAIN_APPROX_NONE = 1,保存物体边界上所有连续的轮廓点到contours向量内；
         * CHAIN_APPROX_SIMPLE = 2,仅保存轮廓的拐点信息，把所有轮廓拐点处的点保存入contours向量内，拐点与拐点之间直线段上的信息点不予保留；
         * CHAIN_APPROX_TC89_L1 = 3,使用teh-Chinl chain 近似算法;
         * CHAIN_APPROX_TC89_KCOS = 4; 使用teh-Chinl chain 近似算法。
         */

        Imgproc.findContours(edges, list, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        /*
         * 绘制轮廓轮廓或填充轮廓。
         * 如果（{thickness}> 0），则该函数在图像中绘制轮廓轮廓；如果（{thickness}<0），则该函数填充轮廓所包围的区域。
         * @param图像目标图像。
         * @param轮廓所有输入轮廓。每个轮廓都存储为点向量。
         * @param outlineIdx指示要绘制的轮廓的参数。如果为负，则绘制所有轮廓。
         * @param color轮廓的颜色。
         * @param thickness绘制轮廓的线的粗细。如果为负（例如，thickness =＃FILLED），则绘制轮廓内部。
         * @param lineType线路连接。请参阅https://blog.csdn.net/ren365880/article/details/103952856
         * /*
         * 绘制连接两个点的线段。 功能线在图像中的pt1和pt2点之间绘制线段。 该线被图像边界剪裁。
         * 对于具有整数坐标的非抗锯齿线，使用8连接或4连接的Bresenham算法。 粗线绘制为圆角。 使用高斯滤波绘制抗锯齿线。
         * @param img图片。
         * @param pt1线段的第一点。
         * @param pt2线段的第二点。
         * @param color线条颜色。
         * @param 厚度线的宽度。
         * @param lineType线的类型。
         *  FILLED = -1,
         *  LINE_4 = 4, 下一个点和上一个点边相连（没有角了），这样就消除了8联通法线断裂的瑕疵
         *  LINE_8 = 8, 下一个点连接上一个点的边或角
         *  LINE_AA = 16; 边缘像素采用高斯滤波，抗锯齿
         */
        Imgproc.drawContours(src, list, -1, new Scalar(0, 255, 0), Imgproc.LINE_4, Imgproc.LINE_AA);

        HighGui.imshow("轮廓", src);
        HighGui.waitKey(0);

    }



}

