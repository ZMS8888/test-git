package com.zm.design_pattern.singleton_mode;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;

public class Test {

/*    public static void main(String[] args) throws IOException {
        SingletonHungry singletonTest = SingletonHungry.getSingletonTest();
        SingletonHungry singletonTest1 = SingletonHungry.getSingletonTest();
        System.out.println(singletonTest1 == singletonTest);


*//*        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonLazy singletonLazy = SingletonLazy.getSingletonTest();

                SingletonLazy singletonLazy1 = SingletonLazy.getSingletonTest();
                System.out.println(singletonLazy == singletonLazy1);

            }, "i" + i).start();
        }*//*
        SingletonLazy singletonLazy = SingletonLazy.getSingletonTest();
        SingletonLazy singletonLazy1 = SingletonLazy.getSingletonTest();
        System.out.println(singletonLazy == singletonLazy1);


        SingletonEnum singleton = SingletonEnum.SINGLETON;
        SingletonEnum singleton1 = SingletonEnum.SINGLETON;
        System.out.println(singleton == singleton1);

        Runtime runtime = Runtime.getRuntime();
        long l = runtime.totalMemory();
        System.out.println(l/1024/1024);
        //创建一个新的进程执行指定的字符串命令，返回进程对象
        Process process = runtime.exec("ipconfig");
        //获取命令执行后的结果，通过输入流获取
        InputStream inputStream = process.getInputStream();
        byte[] arr = new byte[1024 * 1024* 100];
        int b = inputStream.read(arr);
        System.out.println(new String(arr,0,b,"gbk"));
    }*/
}
