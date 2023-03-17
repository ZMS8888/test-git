package com.zm.design_pattern.strategist_mode;


/**
 *具体策略类，实现抽象策略类
 */
public class DrawTriangle implements Strategy{
    @Override
    public void draw() {
        System.out.println("画三角形");
    }
}
