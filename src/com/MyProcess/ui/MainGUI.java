package com.MyProcess.ui;

import com.MyProcess.component.FindCenterOfCircle;
import com.MyProcess.domain.Coordinate;
import com.MyProcess.domain.Image;
import com.MyProcess.component.MySelectCoordinateDialog;
import com.MyProcess.component.MyShowDialog;
import com.MyProcess.util.ScreenUtils;
import org.opencv.dnn.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainGUI {
    List<Coordinate> coordinatesList = new ArrayList<>();
    final int WIDTH = 800;
    final int HEIGHT = 760;

    /*自定义图片*/
    Image[] images = {
            new Image("图片1                   ", new ImageIcon("")),
            new Image("图片2                   ", new ImageIcon("")),
            new Image("图片3                   ", new ImageIcon("")),
    };
    int index = 0;
    boolean hasImage = false;

    JFrame jf = new JFrame("程序accurate");
    // 创建打开文件对话框
    JFileChooser chooser = new JFileChooser("./src/images");

    /* 创建组件*/
    // 创建菜单条
    JMenuBar jMenuBar = new JMenuBar();

    // 创建菜单
    JMenu file = new JMenu("文件");
    JMenu help = new JMenu("帮助");

    // 创建菜单条目
    // 用来记录用户选择的图片
    String currentPath; // 记录当前路径
    int currentIndex = 0; //记录当前图片序号
    JMenuItem openFileItem = new JMenuItem(new AbstractAction("打开文件") {
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

            currentIndex = (index == 0 ? 0 : index % 3 - 1);
            currentPath = imagePath;

            jf.repaint();
            imageCover.repaint();
        }
    });

    JMenuItem exit = new JMenuItem(new AbstractAction("关闭程序") {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });

    // 创建工具条
    JToolBar toolBar = new JToolBar();

    // create Acion steps , then JtoolBar.add(steps)
    Action findCenterOfCircle = new AbstractAction("图片处理-找圆心") {

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
    Action selectBaseVector = new AbstractAction("选择基矢点") {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 自定义设置基矢点对话框 。用户输入基矢点的"上下左右"点
            new MySelectCoordinateDialog().showCustomDialog(jf, jf, coordinatesList, currentIndex % 3);

            // 显示生成的带圆心坐标带箭头的新图片
            images[currentIndex % 3].setName("src_processArrow" + currentIndex % 3 + ".jpg");
            images[currentIndex % 3].setIcon(new ImageIcon("src/images/src_processArrow" + currentIndex % 3 + ".jpg"));

            // 重新刷新
            jf.repaint();
        }
    };

    Action exportCoordinate = new AbstractAction("另存圆心坐标为txt文件") {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 自定义展示坐标对话框  -- 导出
            new MyShowDialog().showCustomDialog(jf, jf, coordinatesList);
           /*  int result = JOptionPane.showConfirmDialog(jf, coordinatesList, "所有圆心坐标", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                System.out.println("ok");
            }*/
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
                System.out.println("(" + e.getX() + "," + e.getY() + ")");
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
