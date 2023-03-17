package com.zm.util.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectToListUtils {

    /**
     * 将单个对象转化为集合
     *
     * @param t   对象实例
     * @param <T> 对象类型
     * @param <C> 集合类型
     * @return 包含对象的集合实例
     */
    public static <T, C extends Collection<T>> Collection<T> toCollection(T t) {
        return toCollection(t, ArrayList::new);
    }

    /**
     * 用户自定义返回的集合实例类型:  将单个对象转化为集合
     *
     * @param t        对象实例
     * @param supplier 集合工厂
     * @param <T>      对象类型
     * @param <C>      集合类型
     * @return 包含对象的集合实例
     */
    public static <T, C extends Collection<T>> Collection<T> toCollection(T t, Supplier<C> supplier) {
        return Stream.of(t).collect(Collectors.toCollection(supplier));
    }


}
