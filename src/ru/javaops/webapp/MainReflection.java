package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Resume resume = new Resume("uuid1");

        // Resume fields
        Field[] resumeFields = resume.getClass().getDeclaredFields();
        for (Field field : resumeFields) {
            field.setAccessible(true);
            System.out.println(field.getType() + " " + field.getName() + " : " + field.get(resume));
        }

        // invoke toString()
        Method resumeToString = resume.getClass().getDeclaredMethod("toString");
        System.out.println(resumeToString.invoke(resume));


    }
}
