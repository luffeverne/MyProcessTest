package com.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.*;

public class test {

    public static void main(String[] args) {
        JFrame jf = new JFrame("test");
        JLabel explainLabel=new JLabel ("说明");

        explainLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mount = 0;
                System.out.println("the mouse is clicked"+(mount++));
            }
        });

        jf.add(explainLabel);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }

}
