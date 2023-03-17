package com.zm.design_pattern.singleton_mode;

/**
 * 懒汉模式
 * 双重加载机制
 */
public class SingletonLazy {

    //使用volatile修饰确保可见性
    private static volatile   SingletonLazy singletonTest = null;

    private SingletonLazy() {

    }
    public static   SingletonLazy getSingletonTest() {
        if (singletonTest == null) {
            //将锁放在if里是减少锁的粒度
            synchronized (SingletonLazy.class) {
                singletonTest = new SingletonLazy();
            }
        }
        return singletonTest;
    }
}
