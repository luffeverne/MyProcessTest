package com.Test;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output2Txt {
    public static void main(String[] args) {
        JTextField jTextArea = new JTextField("hello\n"+"world\n"+"Luffe");

        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileWriter("src/com/Myprocess/output/aaa.txt"));

            // split()里面 居然是" ", 而不是"\n" !!!
            String[] lines = jTextArea.getText().split(" ");

            for (int i = 0; i < lines.length; i++) {
                outputStream.println(lines[i]);
                System.out.println(lines[i]);
            }
            outputStream.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
  
}
