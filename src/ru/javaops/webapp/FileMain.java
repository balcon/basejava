package ru.javaops.webapp;

import java.io.File;

@SuppressWarnings("all")
public class FileMain {
    static void get(File file) {
        System.out.println(file.getPath());
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                get(f);
            }
        }
    }

    public static void main(String[] args) {
        get(new File("."));
    }
}
