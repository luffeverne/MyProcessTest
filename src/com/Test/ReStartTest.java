package com.Test;

import java.io.IOException;

public class ReStartTest {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("hello");
                    Runtime.getRuntime().exec("java ReStartTest");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.exit(0);
    }
}
