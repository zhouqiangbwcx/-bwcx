package com.see.utl;



import java.io.*;
import java.lang.reflect.Method;

/**
 * Created by liuxp on 16/3/12.
 */
public class MyClassLoadert extends ClassLoader {
  
    private String root;
  
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }
  
    private byte[] loadClassData(String className) {
        String fileName = root + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
        System.out.println("asdfsdfdsf");
        try {
            InputStream ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
  
    public String getRoot() {
        return root;
    }
  
    public void setRoot(String root) {
        this.root = root;
    }
  
    public static void main(String[] args)  {
  
        MyClassLoadert classLoader = new MyClassLoadert();
  
        Class<?> testClass = null;
        try {
            testClass = classLoader.loadClass("test");
            Object object = testClass.newInstance();
            
//            Method med=testClass.getMethod("getMacAddress",User.class); 
//            User user=new User();
//            user.setId("ert");
//            med.invoke(object, user);
           
            
            System.out.println(object.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}