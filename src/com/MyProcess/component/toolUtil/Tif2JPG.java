package com.MyProcess.component.toolUtil;


import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.io.*;

/**
 * 可以实现jpg/tif/bmp 等图片之间格式得互相转换
 *
 */
public class Tif2JPG {

    public static String tiffTuanJPG(String filePath){
        String format = filePath.substring(filePath.lastIndexOf(".")+1);
        String turnJpgFile = filePath.replace("tif", "jpg");
        if(format.equals("tif")){
            File fileTiff = new File(turnJpgFile);
            if(fileTiff.exists()){
                System.out.println("该tiff文件已经转换为 JPG 文件："+turnJpgFile);
                return turnJpgFile;
            }
            RenderedOp rd = JAI.create("fileload", filePath);//读取iff文件
            OutputStream ops = null;
            try {
                ops = new FileOutputStream(turnJpgFile);
                //文件存储输出流
                JPEGEncodeParam param = new JPEGEncodeParam();
                ImageEncoder image = ImageCodec.createImageEncoder("JPEG", ops, param); //指定输出格式
                image.encode(rd );
                //解析输出流进行输出
                ops.close();
                System.out.println("tiff转换jpg成功:"+filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return turnJpgFile;
    }

    public static void main(String[] args) throws Exception {
       /* String srcFile = "src/images/1-11- 227Al2O3 Image.tif";
        String descFile = "src/images/test.jpg";
        RenderedOp src = JAI.create("fileload", srcFile);
        OutputStream os = new FileOutputStream(descFile);
        JPEGEncodeParam param = new JPEGEncodeParam();
        //指定格式类型，jpg 属于 JPEG 类型
        ImageEncoder enc = ImageCodec.createImageEncoder("JPEG", os, param);
        enc.encode(src);
        os.close();
*/
        String path = tiffTuanJPG("src/images/1-11- 227Al2O3 Image.tif");

      /*  Mat src = Imgcodecs.imread("path");

        HighGui.imshow("test", src);
*/
    }
}

