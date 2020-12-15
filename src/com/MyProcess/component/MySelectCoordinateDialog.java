package com.MyProcess.component;

import com.MyProcess.domain.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MySelectCoordinateDialog {
    /* 显示一个自定义选择基矢值的对话框
     *
     * @param owner 对话框的拥有者
     * @param parentComponent 对话框的父级组件
     *
     *
     * */
    public static void showCustomDialog(Frame owner,  Component parentComponent, List<Coordinate> arrsList) {

        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "选择基矢点", true);

        // 设置对话框的宽高
//        dialog.setSize(500, 300);

        // 设置对话框的大小不可变
        dialog.setResizable(true);

        // 设置对话框的显示位置
//        dialog.setLocation(300, 300);
        dialog.setLocationRelativeTo(parentComponent);

        // 创建标签的显示消息
        JLabel labelTip = new JLabel("输入基矢点的邻点序号");

        JLabel centerLabel = new JLabel("设为基矢点O的序号：");
        JTextField centerTF = new JTextField(5);

        JLabel upLabel = new JLabel("“上”邻坐标(作为基矢a)序号：");
        JTextField upTF = new JTextField(5);

        JLabel rightLabel = new JLabel("“右”邻坐标(作为基矢b)序号：");
        JTextField rightTF = new JTextField(5);

        JLabel downLabel = new JLabel("“下”邻坐标序号：");
        JTextField downTF = new JTextField(5);

        JLabel leftLabel = new JLabel("“左”邻坐标序号：");
        JTextField leftTF = new JTextField(5);

        JLabel upPlusLabel = new JLabel("基矢a的“上”邻坐标序号：");
        JTextField upPlusTF = new JTextField(5);

        JLabel rightPlusLabel = new JLabel("基矢b的“右”邻坐标序号：");
        JTextField rightPlusTF = new JTextField(5);



        // 创建一个按钮用于“处理”，并关闭弹窗窗口
        JButton btnProcess = new JButton(new AbstractAction("确定") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 等待用户输入 上下左右 的圆心序号，进行计算基矢坐标处理
                String centerString = centerTF.getText();
                String upString = upTF.getText();
                String rightString = rightTF.getText();
                String downString = downTF.getText();
                String leftString = leftTF.getText();
                String upPlusString = upPlusTF.getText();
                String rightPlusString = rightPlusTF.getText();
                System.out.println(upString+" "+rightString+" "+downString+" "+leftString);

                /* 对 O 坐标*/
                // Y: （左+右Y坐标）/2       X : (上+下X坐标) / 2
                double aveOX =(arrsList.get(Integer.parseInt(upString)).getX() + arrsList.get(Integer.parseInt(downString)).getX()) / 2;
                double aveOY =(arrsList.get(Integer.parseInt(leftString)).getY() + arrsList.get(Integer.parseInt(rightString)).getY()) / 2;

                // 再一次进行二分求值, 得到基矢中心的 X、Y 坐标
                double oX = arrsList.get(Integer.parseInt(centerString)).getX();
                double oY = arrsList.get(Integer.parseInt(centerString)).getY();
                aveOX = (aveOX + oX) / 2;
                aveOY = (aveOY + oY) / 2;

                /* 对 a 坐标*/
                // 对基矢a进行处理
                double aX = arrsList.get(Integer.parseInt(upPlusString)).getX();

                /* 对 b 坐标*/
                // TODO 改！！！！！！！
                JOptionPane.showMessageDialog(dialog, "您选择的基矢O"+"("+oX+", "+oY+")\n"+"修正后基矢O"+"("+aveOX+", "+aveOY+")\n" +
                        "您选择的基矢a"+"("+oX+", "+oY+")\n"+"修正后基矢a"+"("+aveOX+", "+aveOY+")\n" +
                        "您选择的基矢b"+"("+oX+", "+oY+")\n"+"修正后基矢b"+"("+aveOX+", "+aveOY+")\n",
                        "基矢值", JOptionPane.PLAIN_MESSAGE);

                System.out.println("("+aveOX+", "+aveOY+")");
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
        labelBox.add(labelTip);


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

        // 组装上上Box
        Box upPlusBox = Box.createHorizontalBox();
        upPlusBox.add(upPlusLabel);
        upPlusBox.add(Box.createHorizontalStrut(5));
        upPlusBox.add(upPlusTF);

        // 组装右右Box
        Box rightPlusBox = Box.createHorizontalBox();
        rightPlusBox.add(rightPlusLabel);
        rightPlusBox.add(Box.createHorizontalStrut(5));
        rightPlusBox.add(rightPlusTF);

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
        box.add(upPlusBox);
        box.add(Box.createVerticalStrut(10));
        box.add(rightPlusBox);
        box.add(Box.createVerticalStrut(10));
        box.add(btnBox);

        panel.add(box);
        dialog.setContentPane(panel);

        // 显示对话框
        dialog.pack();
        dialog.setVisible(true);

    }

}
