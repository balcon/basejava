package ru.javaops.webapp;

import java.io.File;

@SuppressWarnings("all")
public class FileMain {
    private static String marignLeft = "";

    static void get(File file) {
        System.out.println(marignLeft + file.getName());
        if (file.isDirectory()) {
            marignLeft += "  ";
            for (File f : file.listFiles()) {
                get(f);
            }
            marignLeft = marignLeft.substring(0, marignLeft.length() - 2);
        }
    }

    public static void main(String[] args) {
        get(new File("."));
    }
}
