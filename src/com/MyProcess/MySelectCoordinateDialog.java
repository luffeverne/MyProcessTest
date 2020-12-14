package com.MyProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MySelectCoordinateDialog {
    /* 显示一个自定义选择基矢值的对话框
     *
     * @param owner 对话框的拥有者
     * @param parentComponent 对话框的父级组件
     *
     *
     * */
    public static void showCustomDialog(Frame owner, Component parentComponent) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "选择基矢", true);

        // 设置对话框的宽高
//        dialog.setSize(500, 300);

        // 设置对话框的大小不可变
        dialog.setResizable(true);

        // 设置对话框的显示位置
        dialog.setLocation(30, 30);

        // 创建标签的显示消息
        JLabel labelTip = new JLabel("请输入要选择的基矢点的邻点序号");

        JLabel upLabel = new JLabel("“上”邻坐标序号：");
        JTextField upTF = new JTextField(10);

        JLabel rightLabel = new JLabel("“右”邻坐标序号：");
        JTextField rightTF = new JTextField(10);

        JLabel downLabel = new JLabel("“下”邻坐标序号：");
        JTextField downTF = new JTextField(10);

        JLabel leftLabel = new JLabel("“左”邻坐标序号：");
        JTextField leftTF = new JTextField(10);


        // 创建一个按钮用于“处理”，并关闭弹窗窗口
        JButton btnProcess = new JButton(new AbstractAction("确定") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 等待用户输入 上下左右 的圆心序号，进行计算基矢坐标处理

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

        // 组装上Box
        Box upBox = Box.createHorizontalBox();
        upBox.add(upLabel);
        upBox.add(Box.createHorizontalStrut(5));
        upBox.add(upTF);

        // 组装右Box
        Box rightBox = Box.createHorizontalBox();
        upBox.add(rightLabel);
        upBox.add(Box.createHorizontalStrut(5));
        upBox.add(rightTF);

        // 组装下Box
        Box downBox = Box.createHorizontalBox();
        upBox.add(downLabel);
        upBox.add(Box.createHorizontalStrut(5));
        upBox.add(downTF);

        // 组装左Box
        Box leftBox = Box.createHorizontalBox();
        upBox.add(leftLabel);
        upBox.add(Box.createHorizontalStrut(5));
        upBox.add(leftTF);

        // 按钮Box
        Box btnBox = Box.createHorizontalBox();
        btnBox.add(btnProcess);
//        btnBox.add(Box.createVerticalStrut(40));
        btnBox.add(btnCancel);

        Box box = Box.createVerticalBox();

        box.add(labelTip);
        box.add(Box.createVerticalStrut(30));
        box.add(upBox);
        box.add(Box.createVerticalStrut(10));
        box.add(rightBox);
        box.add(Box.createVerticalStrut(10));
        box.add(downBox);
        box.add(Box.createVerticalStrut(10));
        box.add(leftBox);
        box.add(Box.createVerticalStrut(10));
        box.add(btnBox);

        panel.add(box);
        dialog.setContentPane(panel);

        // 显示对话框
        dialog.pack();
        dialog.setVisible(true);

    }

   /* public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setBounds(10, 10 , 300, 500);
        new MySelectCoordinateDialog().showCustomDialog(jf, jf);
    }*/
}
