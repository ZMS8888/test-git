package com.zm;


import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LambdaTest {

    @Test
    public void test() {
       /* adds adds = new adds();
        adds.add(1, 8);*/
       Add add =  (int a,int b)-> {
            System.out.println(a + b);
        };
        add.add(5, 7);
    }

    @Test
    public void test1() {
        List<Integer> integers = Arrays.asList(1, 2);
        integers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });
        integers.forEach(System.out::println);
        integers.forEach(s->{
            System.out.println(s);
        });

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "2");
        hashMap.put("2", "2");
        hashMap.put("3", "2");
        hashMap.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                System.out.println(s + "====" + s2);
            }
        });
        hashMap.forEach((k,v)->{
            System.out.println(k + "==" + v);
        });
    }
}

interface  Add{
    void add(int a, int b);
}

/*class Adds implements Add {

    @Override
    public void add(int a, int b) {
        System.out.println(a + b);
    }
}*/