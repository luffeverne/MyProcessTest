package com.MyProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MyShowDialog {

    /* 显示一个自定义对话框
     *
     * @param owner 对话框的拥有者
     * @param parentComponent 对话框的父级组件
     * @param String[] arrs 显示内容
     *
     * */
    public static void showCustomDialog(Frame owner, Component parentComponent, String[] arrs) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "圆心坐标", true);
        // 设置对话框的宽高
        dialog.setSize(480, 580);

        // 设置对话框大小不可改变
        dialog.setResizable(false);

        // 设置对话框相对显示位置
        dialog.setLocationRelativeTo(parentComponent);

        // 创建标签显示消息
        JTextArea jTextArea = new JTextArea(30, 20);

        // 给标签添加内容
//        String str="";
        for (String str : arrs) {
            if (str == null)
                break;
            jTextArea.append(str+"\n");
        }



        // 创建一个按钮用于关闭对话框
        JButton btnOK = new JButton("确定");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        // 创建对话框的内容面板
        JPanel panel = new JPanel();
        // 组装组件
        panel.add(new JScrollPane(jTextArea));
        panel.add(btnOK, BorderLayout.NORTH);

        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        //显示对话框
        dialog.setVisible(true);

    }
}
