package com.Test;

public class test {

    public static void main(String[] args) {
        String str = "one two three, four";
        String[] tokens = str.split(" ");
        System.out.println("个数:"+tokens.length);
        for (String s: tokens)
            System.out.println(s);
    }
}
