package com.MyProcess.component.dialogUtil;

import com.MyProcess.component.drwaUtil.DrawArrow;
import com.MyProcess.component.matrixUtil.OrderTwoInverseMatrix;
import com.MyProcess.component.drwaUtil.DrawArrowAndNum;
import com.MyProcess.component.drwaUtil.DrawCoordinate;
import com.MyProcess.component.matrixUtil.TransMatrix;
import com.MyProcess.component.matrixUtil.TwoMatrixMultiply;
import com.MyProcess.component.toolUtil.FormatDouble;
import com.MyProcess.domain.Coordinate;
import com.MyProcess.domain.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

public class MySelectCoordinateDialog {
    /* 显示一个自定义选择基矢值的对话框
     *
     * @param owner 对话框的拥有者
     * @param parentComponent 对话框的父级组件
     * @param List<Coordinate> arrsList 所有的圆心坐标的Set集合
     * @param index 记录现在在操作第几张图片
     *
     * */
    public void showCustomDialog(Frame owner, Component parentComponent, List<Coordinate> arrsList, int index, String path) {

        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "对第"+(index+1)+"张图片选择基矢点", true);

        // 设置对话框的宽高
//        dialog.setSize(500, 300);

        // 设置对话框的大小不可变
        dialog.setResizable(true);

        // 设置对话框的显示位置
//        dialog.setLocation(300, 300);
        dialog.setLocationRelativeTo(parentComponent);

        // 创建标签的显示消息
//        JLabel labelTip = new JLabel("输入基矢点O的邻点序号和基矢点a、b");

        JLabel centerLabel = new JLabel("O:");
        JTextField centerTF = new JTextField(5);

        JLabel upLabel = new JLabel("Up:");
        JTextField upTF = new JTextField(5);

        JLabel rightLabel = new JLabel("Right:");
        JTextField rightTF = new JTextField(5);

        JLabel downLabel = new JLabel("Down:");
        JTextField downTF = new JTextField(5);

        JLabel leftLabel = new JLabel("Left:");
        JTextField leftTF = new JTextField(5);

        JLabel aCoordinate = new JLabel("a:");
        JTextField aCoordinateTF = new JTextField(5);

        JLabel bCoordinate = new JLabel("b:");
        JTextField bCoordinateTF = new JTextField(5);


        // 创建一个“确定”按钮用于处理修正基矢值的作用，并关闭弹窗窗口
        JButton btnProcess = new JButton(new AbstractAction("确定") {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 声明 基矢的长度
                double aVectorLen = 0, bVectorLen = 0;

                // TODO 等待用户输入 上下左右 的圆心序号，进行计算基矢坐标处理
                String centerString = centerTF.getText();
                String upString = upTF.getText();
                String rightString = rightTF.getText();
                String downString = downTF.getText();
                String leftString = leftTF.getText();
                String aString = aCoordinateTF.getText();
                String bString = bCoordinateTF.getText();

                /*测试，赋值它们，待删*/
           /*     centerString = "29";
                upString = "37";
                rightString = "27";
                downString = "20";
                leftString = "28";
                aString = "37";
                bString = "32";
*/

                /* 对 O 坐标*/
                //  X: （左+右X坐标）/2 ,  Y : (上+下Y坐标) / 2
                double aveOX = (arrsList.get(Integer.parseInt(leftString)).getX() + arrsList.get(Integer.parseInt(rightString)).getX()) / 2;
                double aveOY = (arrsList.get(Integer.parseInt(upString)).getY() + arrsList.get(Integer.parseInt(downString)).getY()) / 2;

                // 再一次进行二分求值, 得到基矢中心的 X、Y 坐标
                double oX = arrsList.get(Integer.parseInt(centerString)).getX();
                double oY = arrsList.get(Integer.parseInt(centerString)).getY();
                aveOX = (aveOX + oX) / 2;
                aveOY = (aveOY + oY) / 2;

                // TODO 求出所有m、n   ： (x, y) = m * 矢量a + n * 矢量b， 然后再反求回 矢量a， b的坐标
                /* 对 a 坐标*/
                double aX = arrsList.get(Integer.parseInt(aString)).getX();
                double aY = arrsList.get(Integer.parseInt(aString)).getY();

                /* 对 b 坐标*/
                double bX = arrsList.get(Integer.parseInt(bString)).getX();
                double bY = arrsList.get(Integer.parseInt(bString)).getY();



                // mMatrixT[m][n]
                double[][] mMatrix = new double[arrsList.size()][2];
                double[][] mMatrixT = new double[2][arrsList.size()];
                double[][] rMatrix = new double[arrsList.size()][2];


                int size = arrsList.size();


                // 计算出矩阵M 以及 初始化矩阵R
                for (int i = 0; i < size; i++) {
                    Coordinate c = arrsList.get(i);

                    rMatrix[i][0] = c.getX();
                    rMatrix[i][1] = c.getY();

                    mMatrix[i][0] = (bY * c.getX() - bX * c.getY()) / (aX * bY - bX * aY);
                    mMatrix[i][1] = (aY * c.getX() - aX * c.getY()) / (bX * aY - aX * bY);

                  /*  // TODO 图中标的坐标需要重绘一下
                    if (i == 29) {
                        System.out.print("i来啦！");
                    }
                    System.out.println((int)mMatrix[i][0] + "," + (int)mMatrix[i][1]);*/
                }

                // 标出新的坐标
                new DrawCoordinate().drawCoordinateOfNew(index, mMatrix, arrsList);

                // 矩阵M转置
                mMatrixT =  TransMatrix.getTransMatrix(mMatrix);

                /* for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < size; j++) {
                        mMatrixT[i][j] = mMatrix[j][i];
                    }
                }*/

                // 转置M * M
                double[][] mTm = new TwoMatrixMultiply().Multiply(mMatrixT, mMatrix);

                // （转置M * M） 的逆矩阵
                double[][] revMatrix = new OrderTwoInverseMatrix(). getMartrixResult(mTm);

                // (（转置M * M） 的逆矩阵 ) * 转置M
                double[][] tMatrix = new TwoMatrixMultiply().Multiply(revMatrix, mMatrixT);


                //  (（转置M * M） 的逆矩阵 ) * 转置M  * R
                double[][] resMatrix = new TwoMatrixMultiply().Multiply(tMatrix, rMatrix);


                // 输出结果
                double reviseAX = resMatrix[0][0];
                double reviseAY = resMatrix[0][1];
                double reviseBX = resMatrix[1][0];
                double reviseBY = resMatrix[1][1];

                FormatDouble fd = new FormatDouble();

                JOptionPane.showMessageDialog(dialog, "您选择的基矢O" + "(" + oX + ", " + oY + ")\n" + "修正后基矢O" + "(" + fd.formatDouble(aveOX) + ", " + fd.formatDouble(aveOY) + ")\n\n" +
                                "您选择的基矢a" + "(" + aX + ", " + aY + ")\n" +  "修正后基矢A" + "(" + fd.formatDouble(reviseAX) + ", " + fd.formatDouble(reviseAY) + ")\n\n" +
                                "您选择的基矢b" + "(" + bX + ", " + bY + ")\n" +  "修正后基矢B" + "(" + fd.formatDouble(reviseBX) + ", " + fd.formatDouble(reviseBY) + ")\n\n",
                        "基矢值", JOptionPane.PLAIN_MESSAGE);

                // 标出矢量a, b
//                new DrawArrow().drawArrowOfAB(index, oX, oY, reviseAX, reviseAY, reviseBX, reviseBY);

                // 标出矢量a，b，以及更新坐标
                new DrawArrowAndNum().drawArrowOfAB(index, oX, oY, reviseAX, reviseAY, reviseBX, reviseBY, mMatrix, arrsList);

                aVectorLen = fd.formatDouble(Math.sqrt((reviseAX - aveOX) * (reviseAX - aveOX) + (reviseAY - aveOY) * (reviseAY - aveOY)));
                bVectorLen = fd.formatDouble(Math.sqrt((reviseBX - aveOX) * (reviseBX - aveOX) + (reviseBY - aveOY) * (reviseBY - aveOY)));

                System.out.println(aVectorLen+ ",   "+ bVectorLen);




                // ToDo 输入到txt文档
                //如果有3对了，把基矢值的长度都存在一个txt文档中
                if (index == 2) {

                    try {
                        FileReader file = new FileReader("src/com/Myprocess/output/vectorTest.txt");
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
                dialog.dispose();
            }
        });


        


        // 创建一个按钮用于“取消”，不进行处理，并关闭弹窗窗口
        JButton btnCancel = new JButton(new AbstractAction("取消") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        // 创建对话框的内容面板
        JPanel panel = new JPanel();

        // 组装Label
        Box labelBox = Box.createHorizontalBox();
        labelBox.add(Box.createHorizontalStrut(10));
//        labelBox.add(labelTip);


        // 组装中心Box
        Box centerBox = Box.createHorizontalBox();
        centerBox.add(centerLabel);
        centerBox.add(Box.createHorizontalStrut(5));
        centerBox.add(centerTF);

        // 组装上Box
        Box upBox = Box.createHorizontalBox();
        upBox.add(upLabel);
        upBox.add(Box.createHorizontalStrut(5));
        upBox.add(upTF);

        // 组装右Box
        Box rightBox = Box.createHorizontalBox();
        rightBox.add(rightLabel);
        rightBox.add(Box.createHorizontalStrut(5));
        rightBox.add(rightTF);

        // 组装下Box
        Box downBox = Box.createHorizontalBox();
        downBox.add(downLabel);
        downBox.add(Box.createHorizontalStrut(5));
        downBox.add(downTF);

        // 组装左Box
        Box leftBox = Box.createHorizontalBox();
        leftBox.add(leftLabel);
        leftBox.add(Box.createHorizontalStrut(5));
        leftBox.add(leftTF);

        // 组装基矢点aBox
        Box aBox = Box.createHorizontalBox();
        aBox.add(aCoordinate);
        aBox.add(Box.createHorizontalStrut(5));
        aBox.add(aCoordinateTF);

        // 组装基矢点bBox
        Box bBox = Box.createHorizontalBox();
        bBox.add(bCoordinate);
        bBox.add(Box.createHorizontalStrut(5));
        bBox.add(bCoordinateTF);

        // 按钮Box
        Box btnBox = Box.createHorizontalBox();
        btnBox.add(Box.createHorizontalStrut(30));
        btnBox.add(btnProcess);
        btnBox.add(Box.createHorizontalStrut(40));
        btnBox.add(btnCancel);

        Box box = Box.createVerticalBox();

        box.add(labelBox);
        box.add(Box.createVerticalStrut(20));
        box.add(centerBox);
        box.add(Box.createVerticalStrut(10));
        box.add(upBox);
        box.add(Box.createVerticalStrut(10));
        box.add(rightBox);
        box.add(Box.createVerticalStrut(10));
        box.add(downBox);
        box.add(Box.createVerticalStrut(10));
        box.add(leftBox);
        box.add(Box.createVerticalStrut(10));
        box.add(aBox);
        box.add(Box.createVerticalStrut(10));
        box.add(bBox);
        box.add(Box.createVerticalStrut(10));
        box.add(btnBox);

        panel.add(box);
        dialog.setContentPane(panel);

        // 显示对话框
        dialog.pack();
        dialog.setVisible(true);

    }

}
