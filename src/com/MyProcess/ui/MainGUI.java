package com.MyProcess.ui;

import com.MyProcess.component.drwaUtil.FindCenterOfCircle;
import com.MyProcess.component.matrixUtil.TransMatrix;
import com.MyProcess.component.matrixUtil.TwoMatrixMultiply;
import com.MyProcess.component.toolUtil.FormatDouble;
import com.MyProcess.domain.Coordinate;
import com.MyProcess.domain.Image;
import com.MyProcess.component.dialogUtil.MySelectCoordinateDialog;
import com.MyProcess.component.dialogUtil.MyShowDialog;
import com.MyProcess.util.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;


public class MainGUI {
    List<Coordinate> coordinatesList = new ArrayList<>();
    final int WIDTH = 1000;
    final int HEIGHT = 900;

    /*自定义图片*/
    Image[] images = {
            new Image("Picture1                    ", new ImageIcon("")),
            new Image("Picture2                    ", new ImageIcon("")),
            new Image("Picture3                    ", new ImageIcon("")),
    };
    int index = 0;
    boolean hasImage = false;

    JFrame jf = new JFrame("accurate");
    // 创建打开文件对话框
    JFileChooser chooser = new JFileChooser("./src/images");

    /* 创建组件*/
    // 创建菜单条
    JMenuBar jMenuBar = new JMenuBar();

    // 创建菜单
    JMenu file = new JMenu("File");
    JMenu help = new JMenu("Help");

    // 创建菜单条目
    // 用来记录用户选择的图片
    String currentPath; // 记录当前路径
    int currentIndex = 0; //记录当前图片序号
    JMenuItem openFileItem = new JMenuItem(new AbstractAction("open file") {
        @Override
        public void actionPerformed(ActionEvent e) {
            hasImage = true;

            chooser.showOpenDialog(imageCover);
            File imageFile = chooser.getSelectedFile();

            String imageName = imageFile.getName();
            String imagePath = imageFile.getPath();

            boolean isTif = imageName.endsWith(".tif");
            if (isTif) {
                String str = imageName.replaceAll(".tif", ".jpg");
                System.out.println(str);

            }

            images[index % 3].setName(imageName);
            images[index % 3].setIcon(new ImageIcon(imagePath));
            index++;


            if (index != 3) {
                currentIndex = (index % 3 == 0 ? 0 : index - 1);
            } else {
                currentIndex = 2;
            }
            currentPath = imagePath;

            jf.repaint();
            imageCover.repaint();
        }
    });

    JMenuItem exit = new JMenuItem(new AbstractAction("exit") {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });

    // 创建工具条
    JToolBar toolBar = new JToolBar();

    // create Acion steps , then JtoolBar.add(steps)
    Action findCenterOfCircle = new AbstractAction("find center") {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 保存所有坐标值
            coordinatesList = new FindCenterOfCircle().handlePicture(currentPath, currentIndex);

            // 验证所有圆心坐标
           /* for (int i = 0; i < coordinatesList.size(); i++) {
                Coordinate c = coordinatesList.get(i);
                System.out.println(c.getX() + "," + c.getY());
            }*/
            // 显示生成的带圆心坐标的新图片
            images[currentIndex % 3].setName("src_process" + currentIndex % 3 + ".jpg");
            images[currentIndex % 3].setIcon(new ImageIcon("src/images/src_process" + currentIndex % 3 + ".jpg"));

            // 重新刷新
            jf.repaint();
        }
    };
    Action selectBaseVector = new AbstractAction("set Coordinate") {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 自定义设置基矢点对话框 。用户输入基矢点的"上下左右"点
            new MySelectCoordinateDialog().showCustomDialog(jf, jf, coordinatesList, currentIndex, currentPath);

            // 显示生成的带圆心坐标带箭头的新图片
            images[currentIndex % 3].setName("src_processArrowAndNewCoo" + currentIndex % 3 + ".jpg");
            images[currentIndex % 3].setIcon(new ImageIcon("src/images/src_processArrowAndNewCoo" + currentIndex % 3 + ".jpg"));

            // 重新刷新
            jf.repaint();

        }
    };

    Action exportCoordinate = new AbstractAction("export") {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 自定义展示坐标对话框  -- 导出
            new MyShowDialog().showCustomDialog(jf, jf, coordinatesList, currentIndex);
           /*  int result = JOptionPane.showConfirmDialog(jf, coordinatesList, "所有圆心坐标", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                System.out.println("ok");
            }*/
        }
    };

    // 输出空间信息
    Action exportInfo = new AbstractAction("information") {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rLen = 11;

            double[] vector = new double[6]; // 6个基矢向量
            double[] aveVector = new double[3]; // 3个平均后的基矢向量值
            double[][] matrix = new double[3][2]; // A矩阵，3个基矢的x,y坐标
            double[][] tMatrix = new double[2][3]; // 转置A矩阵
            double[][] aaMatrix = new double[3][3]; // 计算AA矩阵
            double[] t = new double[18]; // 暂存


            double[][] rMatrix = new double[rLen][3]; // 11 * 3
            double[][] rTmatrix = new double[3][rLen]; // 3 * 11   r矩阵的转置
            double[][] rrMatrix = new double[rLen][rLen]; // 11 * 11
            double[][] array = new double[rLen * (rLen-1)][4];
//            double[][] arrayIndex = new double[rLen * (rLen-1)][3];

          /*  *//* 初始化 rMatrix*//*
            for (int i = 0, start = -5; i < rLen; i++, start++) {
                for (int j = 0; j < 3; j++) {
                    rMatrix[i][j] = start;
//                    System.out.print(rMatrix[i][j] + " ");
                }
//                System.out.println(" ");
            }
            rTmatrix = TransMatrix.getTransMatrix(rMatrix);*/


            //TODO！！ 打开txt文件  以后用数据库来存
            try (FileReader reader = new FileReader("src/com/Myprocess/output/vector.txt");
                BufferedReader br = new BufferedReader(reader);
            ) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
//                    System.out.println(line);
                    vector[i] = Double.valueOf(line);
//                    System.out.println(i + ": " + vector[i]);
                    i++;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            // 基矢取平均值
            // 先排序，再取平均值
            Arrays.sort(vector);
          /*  for (int i = 0; i < 6; i++) {
                 System.out.println(i + " " + vector[i]);
            }*/

            FormatDouble fd = new FormatDouble();
            aveVector[0] = fd.formatDouble (vector[0] + vector[1]) / 2;
            aveVector[1] = fd.formatDouble (vector[2] + vector[3]) / 2;
            aveVector[2] = fd.formatDouble (vector[4] + vector[5]) / 2;

            // 基矢的平均长度
            System.out.println("3个基矢的平均长度");
            for (int i = 0; i < 3; i++) {
                System.out.println(aveVector[i]);
            }


            // 计算 AA矩阵
            // 读取出基矢向量
            try (FileReader fileReader = new FileReader("src/com/Myprocess/output/baseVector.txt");
                BufferedReader br = new BufferedReader(fileReader);
            ) {
                String line;
                int i = 0;
                while((line = br.readLine()) != null) {
//                    System.out.println(line);
                    t[i] = Double.parseDouble(line);
//                    System.out.println(t[i]);
                    i++;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            // 每张基矢a和b之间的夹角
            System.out.println("每张基矢a和b之间的夹角:");
            for (int i = 0, j = 0; j < 3; j++) {
                double test = (t[i] * t[i+2] + t[i+1] * t[i+3]) / (Math.sqrt(t[i] * t[i] + t[i+1] * t[i+1]) * Math.sqrt(t[i+2] * t[i+2] + t[i+3] * t[3]));
                System.out.println("angle:" + (Math.acos(test) * (180 / Math.PI))+"°");
                i += 6;
            }
            /*double test = (t[0] * t[2] + t[1] * t[3]) / (Math.sqrt(t[0] * t[0] + t[1] * t[1]) * Math.sqrt(t[2] * t[2] + t[3] * t[3]));
            System.out.println("angle1:" + (Math.acos(test) * (180 / Math.PI))+"°");
            test = (t[6] * t[8] + t[7] * t[9]) / (Math.sqrt(t[6] * t[6] + t[7] * t[7]) * Math.sqrt(t[8] * t[8] + t[9] * t[9]));
            System.out.println("angle1:" + (Math.acos(test) * (180 / Math.PI))+"°");
            test = (t[0] * t[2] + t[1] * t[3]) / (Math.sqrt(t[0] * t[0] + t[1] * t[1]) * Math.sqrt(t[2] * t[2] + t[3] * t[3]));
            System.out.println("angle1:" + (Math.acos(test) * (180 / Math.PI))+"°");*/


        /*    double sin = t[0] * t[3] - t[2] * t[1];
            double cos = t[0] * t[1] + t[1] * t[3];
            System.out.println("angle:" + Math.atan2(sin, cos) * (180 / Math.PI));*/




          /*
            // 求出NaN了， resTest是1.7236301465374249
            double aVectorX = t[0] - t[4];
            double aVectorY = t[1] - t[5];
            double bVectorX = t[2] - t[4];
            double bVectorY = t[3] - t[5];

            System.out.println(aVectorX+ " " + aVectorY +" " +  bVectorX +" " +  bVectorY);
            double resTest = (aVectorX * bVectorX + bVectorY * bVectorY) / ((Math.sqrt(aVectorX * aVectorX + aVectorY * aVectorY)) * (Math.sqrt(bVectorX * bVectorX + bVectorY * bVectorY)));
            System.out.println(resTest);
            System.out.println("angle:" + (Math.acos(resTest) * (180 / Math.PI)));*/



            double aveDArrayX = (t[4] + t[10] + t[16]) / 3;
            double aveDArrayY = (t[5] + t[11] + t[17]) / 3;

            // maxtrix 的横坐标
            for (int i = 0, j = 0; i < 3; i++, j += 2) {
                matrix[i][0] = fd.formatDouble(t[j] - aveDArrayX);
                matrix[i][1] = fd.formatDouble(t[j + 1] - aveDArrayY);
            }

           /* for (int i = 0; i < 3; i++) {
                System.out.println(matrix[i][0] + ", " + matrix[i][1]);
            }*/



            tMatrix = TransMatrix.getTransMatrix(matrix);

            aaMatrix = TwoMatrixMultiply.Multiply(matrix, tMatrix);
            for (int i = 0; i < aaMatrix.length; i++) {
                for (int j = 0; j < aaMatrix[0].length; j++) {
                    aaMatrix[i][j] = FormatDouble.formatDouble(aaMatrix[i][j]);
//                    System.out.print(aaMatrix[i][j] + " ");
                }
//                System.out.println("\n");
            }


            // 计算 r^2 ， 用i、j、k 从-5到5开始循环 。注意每个值都要记录下该i，j，k

            /*
            rMatrix = TwoMatrixMultiply.Multiply(rMatrix, aaMatrix);

            rrMatrix = TwoMatrixMultiply.Multiply(rMatrix, rTmatrix);
            for (int i = 0; i < rLen; i++) {
                for (int j = 0; j < rLen; j++) {
                    rrMatrix[i][j] = FormatDouble.formatDouble(rrMatrix[i][j]);
//                    System.out.print(rrMatrix[i][j] + " ");
                }
//                System.out.print("\n");
            }*/

            int l = 0;
            for (int i = 5; i <=5 ; i++) {
                for (int j = -5; j <= 5; j++) {
                    for (int k = -5; k < 5; k++) {


                        double[][] d = new double[1][3];
                        double[][] dT = new double[3][1];
                        d[0][0] = dT[0][0] = i;
                        d[0][1] = dT[1][0] = j;
                        d[0][2] = dT[2][0] = k;

//                        System.out.println(i + " " + j + " " + k);

                        double[][] multiply = TwoMatrixMultiply.Multiply(d, aaMatrix);
                        double[][] multiply1 = TwoMatrixMultiply.Multiply(multiply, dT);

//                        System.out.println(multiply1.length + " ," + multiply1[0].length);
                        double res = multiply1[0][0];
                        res = FormatDouble.formatDouble(res);
//                        System.out.println(res);

                        // 记录数组及坐标
                        array[l][0] = res;
                        array[l][1] = i;
                        array[l][2] = j;
                        array[l][3] = k;

//                        System.out.println(array[l][0] + "," + array[l][1] + "," + array[l][2] + " " + array[l][3]);
                        l++;

                    }
                }
            }



            // 排序 r值 ， 有相等的
//            Arrays.sort(array);
//            找三个最小的值
            double min;
            double[] threeMin =  new double[3];
            double[][] td = new double[3][3];
            double[] d1 = new double[3];
            double[] d2 = new double[3];
            double[] d3 = new double[3];


            int minIndex = 0;
            int tdIndex = 0;
            for (int i = 0; i < 3; i++) {
                min = array[0][0];

                for (int j = 0; j < l; j++) {
                    if (array[j][0] != -0.000000001 && min > array[j][0]) {
                        min = array[j][0];
                        minIndex = j;
                    }
                }
                threeMin[i] = min;
//                System.out.println(minIndex);
//                System.out.println(threeMin[i] + " haha:" + array[minIndex][0] + " " + array[minIndex][1] + " " + array[minIndex][2] + " " + array[minIndex][3]);

                // 将最小值存入到二维数组d中
                // 取r值三个 非零的、不相等的数，作为d1，d2，d3
                for (int j = 0; j < 3; j++) {
                    td[tdIndex][j] = array[minIndex][j+1];
//                    System.out.println(d[dIndex][j]);
                }
                tdIndex++;
                array[minIndex][0] = -0.000000001;
            }

            // 检测d
    /*        for (int i = 0; i < td.length; i++) {
                for (int j = 0; j < td[0].length; j++) {
                    System.out.print(td[i][j] + " ");
                }
                System.out.print("\n");
            }*/


            for (int i = 0; i < 3; i++) {
                d1[i] = td[0][i];
                d2[i] = td[1][i];
                d3[i] = td[2][i];
            }


            // 计算出这三个最短矢量的夹角


            // 计算Niggli矩阵参数
            double s11 = d1[0] * d1[0] + d1[1] * d1[1] + d1[2] * d1[2];
            double s22 = d2[0] * d2[0] + d2[1] * d2[1] + d2[2] * d2[2];
            double s33 = d3[0] * d3[0] + d3[1] * d3[1] + d3[2] * d3[2];


            double[] ts23 = TwoMatrixMultiply.Multiply1(d3, aaMatrix);
            double s23;
            double[][] d2T = TransMatrix.getTransMatrix1(d2);
            ts23 = TwoMatrixMultiply.Multiply1(ts23, d2T);
            s23 = FormatDouble.formatDouble(ts23[0]);

            double[] ts13 = TwoMatrixMultiply.Multiply1(d1, aaMatrix);
            double s13;
            double[][] d3T = TransMatrix.getTransMatrix1(d3);
            ts13 = TwoMatrixMultiply.Multiply1(ts13, d3T);
            s13 = FormatDouble.formatDouble(ts13[0]);

            double[] ts12 = TwoMatrixMultiply.Multiply1(d1, aaMatrix);
            double s12;
            ts12 = TwoMatrixMultiply.Multiply1(ts12, d2T);
            s12 = FormatDouble.formatDouble(ts12[0]);


            System.out.println("\ns11,s22,s33,s23,s13,s12的值：");
            System.out.println(s11 + " "  + s22  + " "  + s33);
            System.out.println(s23 + " "  + s13  + " "  + s12);

            // 暂时：根据上面的参数，手动确定约化胞序号

        }
    };

    // 创建分隔栏
    // 左边显示图片名称
    JList<Image> imagesJList = new JList<>(images);
    // 右边显示图片
    JLabel imageCover = new JLabel();


    public void init() {
        /* 组装视图*/
        // 组装菜单条
        file.add(openFileItem);
        file.add(exit);
        jMenuBar.add(file);
        jMenuBar.add(help);
        jf.setJMenuBar(jMenuBar);

        // 组装工具条
        toolBar.add(findCenterOfCircle);
        toolBar.add(selectBaseVector);
        toolBar.add(exportCoordinate);
        toolBar.add(exportInfo);
        jf.add(toolBar, BorderLayout.NORTH);

        // 组装分割面板
        imagesJList.setPreferredSize(new Dimension(220, 800));
        imageCover.setPreferredSize(new Dimension(780, 800));


        // add Action Listener for imagesJList
     /*   imagesJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Image image = imagesJList.getSelectedValue();
                imageCover.setIcon(image.getIcon());
            }
        });*/

        // 为imagesJList注册监听 lambda格式
        imagesJList.addListSelectionListener(e -> {
            Image image = imagesJList.getSelectedValue();
            imageCover.setIcon(image.getIcon());
        });

        // 为JLabel注册鼠标监听
        imageCover.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取当前JLabel窗口的坐标
//                System.out.println("(" + e.getX() + "," + e.getY() + ")");
                // 自定义对话框 -设置基矢点。用户输入基矢点的"上下左右"点

            }
        });


        //create a horizontal split pane
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagesJList, imageCover);
//        pane.setOneTouchExpandable(true);
        pane.setContinuousLayout(true);
        pane.resetToPreferredSizes();
        pane.setDividerSize(10);
        jf.add(pane);


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.pack();
        jf.setBounds(ScreenUtils.getScreenWidth() / 2 - WIDTH / 2, ScreenUtils.getScreenHeight() / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI().init();
    }
}
