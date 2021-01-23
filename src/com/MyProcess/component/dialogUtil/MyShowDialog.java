package com.MyProcess.component.dialogUtil;

import com.MyProcess.domain.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class MyShowDialog {

    /* 显示一个自定义显示所有坐标值的对话框
     *
     * @param owner 对话框的拥有者
     * @param parentComponent 对话框的父级组件
     * @param String[] arrs 显示内容
     *
     * */
    public  void showCustomDialog(Frame owner, Component parentComponent, List<Coordinate> arrsList, int index) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "圆心坐标", true);
        // 设置对话框的宽高
        dialog.setSize(480, 580);

        // 设置对话框大小不可改变
        dialog.setResizable(false);

        // 设置对话框相对显示位置
        dialog.setLocationRelativeTo(parentComponent);

        // 创建文本域显示坐标信息
        JTextArea jTextArea = new JTextArea(30, 20);

        // 给文本域添加内容
        for (int i = 0; i < arrsList.size(); i++) {
            Coordinate c = arrsList.get(i);
            jTextArea.append(i + " " + c.getX() + " " + c.getY() + "\n");
        }


        // 创建一个按钮用于关闭对话框
        JButton btnExport = new JButton(new AbstractAction("导出") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 保存文件流，导成txt文件格式
                PrintWriter outputStream = null;
                try {
                    outputStream = new PrintWriter(new FileWriter("src/com/Myprocess/output/AllCoordinates"+index+".txt"));

                    String[] lines = jTextArea.getText().split(" ");
                    for (int i = 0; i < lines.length; i++) {
//                        System.out.print(lines[i]);
                        outputStream.write(lines[i]+" ");
                    }
                    outputStream.close();


                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(dialog, "导出到AllCoordinates.txt成功", "提示", JOptionPane.PLAIN_MESSAGE);
                dialog.dispose();

            }
        });

        // 创建对话框的内容面板
        JPanel panel = new JPanel();
        // 组装组件
        panel.add(new JScrollPane(jTextArea));
        panel.add(btnExport, BorderLayout.NORTH);

        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        //显示对话框
        dialog.setVisible(true);

    }
}
