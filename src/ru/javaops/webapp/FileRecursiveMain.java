package ru.javaops.webapp;

import java.io.File;
import java.util.Objects;

public class FileRecursiveMain {
    private static String marignLeft = "";

    static void get(File file) {
        System.out.println(marignLeft + file.getName());
        if (file.isDirectory()) {
            marignLeft += "  ";
            for (File f : Objects.requireNonNull(file.listFiles())) {
                get(f);
            }
            marignLeft = marignLeft.substring(0, marignLeft.length() - 2);
        }
    }

    public static void main(String[] args) {
        get(new File("."));
    }
}
