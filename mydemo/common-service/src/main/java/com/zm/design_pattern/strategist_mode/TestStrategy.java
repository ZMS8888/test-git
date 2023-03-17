package com.zm.design_pattern.strategist_mode;

public class TestStrategy {
    /**
     * 策略模式优点
     *      1、多重条件语句不易维护，而使用策略模式可以避免使用多重条件语句。
     *      2、策略模式提供了一系列的可供重用的算法族，恰当使用继承可以把算法族的公共代码转移到父类里面，从而避免重复的代码。
     *      3、策略模式可以提供相同行为的不同实现，客户可以根据不同时间或空间要求选择不同的。
     *      4、策略模式提供了对开闭原则的完美支持，可以在不修改原代码的情况下，灵活增加新算法。
     *      5、策略模式把算法的使用放到环境类中，而算法的实现移到具体策略类中，实现了二者的分离。
     * 缺点：
     *      1、客户端必须理解所有策略算法的区别，以便适时选择恰当的算法类。
     *      2、策略模式造成很多的策略类。
     * @param args
     */
  /*  public static void main(String[] args) {
        Strategy strategy = new DrawCircular();
        StrategyContext strategyContext = new StrategyContext(strategy);
        Strategy drawTriangle = new DrawTriangle();
        StrategyContext strategyContext1 = new StrategyContext(drawTriangle);
        strategyContext.draw();
        strategyContext1.draw();

    }*/
}
