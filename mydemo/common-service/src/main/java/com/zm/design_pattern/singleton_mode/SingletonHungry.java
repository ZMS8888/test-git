package com.zm.design_pattern.singleton_mode;

/**
 * 恶汉模式
 */
public class SingletonHungry {

    private static final SingletonHungry singletonTest = new SingletonHungry();
    private SingletonHungry() {
    }

    public static SingletonHungry getSingletonTest() {
        return singletonTest;
    }
}
