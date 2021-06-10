package com.ilyin.stringslab;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String pattern = "pata";
        String text = "ABRACADABRA";

        BoyerMoore boyermoore1 = new BoyerMoore(pattern);
        int offset1 = boyermoore1.search(System.in);

        // print results
        //System.out.println("text:    " + text);

        System.out.print("pattern: ");
        //for (int i = 0; i < offset1; i++)
        //    System.out.print(" ");
        System.out.println(pattern);

    }
}