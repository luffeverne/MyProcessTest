/*
package com.MyProcess;

import java.io.FileOutputStream;

public class Tif2JPG {
    public static void main(String[] args) {
        */
/* tif转换到jpg格式 *//*

        String input2 = "d:/img/a.tif";
        String output2 = "d:/img/a.jpg";
        RenderedOp src2 = JAI.create("fileload", input2);
        OutputStream os2 = new FileOutputStream(output2);
        JPEGEncodeParam param2 = new JPEGEncodeParam();
        //指定格式类型，jpg 属于 JPEG 类型
        ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2, param2);
        enc2.encode(src2);
        os2.close();
    }
}
*/
