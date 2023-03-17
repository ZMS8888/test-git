package com.zm;

import com.zm.entity.UserEntity;
import com.zm.util.operation.ObjectToListUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {




    private static Stream<String> init() {
        Stream<String> list = Stream.of("zm", "szmdz", "ksls", "slz");
        return list;
    }

    private static List<UserEntity> initUser() {
        List<UserEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i / 2 == 0) {
                UserEntity userEntity = UserEntity.builder()
                        .age(i)
                        .userName("zm" + i)
                        .password("123456" + i)
                        .build();
                list.add(userEntity);
            } else {
                UserEntity userEntity = UserEntity.builder()
                        .age(i)
                        .userName("zm"+i)
                        .password("123456")
                        .build();
                list.add(userEntity);
            }

        }
       return list;
    }



    @Test
    public void testForeach() {
        Stream<String> init = init();
        init.forEach(s -> {
            System.out.println(s);
        });
    }

    /**
     *  过滤，延迟方法，接收判断类型的函数式接口，产生新的子集流，调用后还可以继续调用其它的Stream流方法
     */
    @Test
    public void testFilter() {
        Stream<String> init = init();
        Stream<String> names = init.filter((String name) -> {
            return name.startsWith("z") && name.length() == 2;
        });
        names.forEach(n->{
            System.out.println(n);
        });
    }

    /**
     * 延迟方法，操作Stream流数据中的每个元素，将Stream流映射到一个新的Stream流上
     */
    @Test
    public void testMap() {
        Stream<String> init = init();
      /*  init.map(name -> {
            return name = name.substring(2);
        }).forEach(name-> System.out.println(name));*/
        List<String> collect = init.map(s -> s.substring(2)).collect(Collectors.toList());
        collect.forEach(System.out::println);
        List<UserEntity> list = initUser();
        List<Integer> collect1 = list.stream().map(UserEntity::getAge).collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    /**
     * 	接收一个函数作为参数，将流中的每个值都换成另一个流，
     * 	然后把所有流连接成一个流。
     */
    @Test
    public void testFlatMap() {
        List<String> strings = Arrays.asList("a,b,c", "cd,d");
        List<String> collect = strings.stream().flatMap(s -> {
            String[] split = s.split(",");
            Stream<String> stream = Arrays.stream(split);
            return stream;
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     * 延迟方法，截取Stream流中的前几个元素返回新的Stream流，入参为long类型，没有方法体
     * 若入参的值大于Stream流中的数据的长度则返回由原数据组成的新Stream流
     */
    @Test
    public void testLimit() {
        Stream<String> init = init();
        init.limit(3).forEach(s -> {
            System.out.println(s);
        });
    }


    /**
     *延迟方法，入参为long类型，没有方法体，跳过前一个Stream流的前几个元素，得到由后面的元素组成的新Stream流
     *若跳过的元素个数>=Stream流数据的长度则会得到一个元素个数为0的空流
     * */
    @Test
    public void testSkip() {
        Stream<String> init = init();
        init.skip(2).forEach(s -> System.out.println(s));
    }

    /**
     * 最终方法，没有参数，没有方法体，属于Stream流的最终方法，用于统计Stream流中的数据长度，返回long类型
     */
    @Test
    public void testCount() {
        Stream<String> init = init();
        long count = init.count();
        System.out.println(count);
    }

    /**
     * 查找首个或任意一个
     */
    @Test
    public void testFind() {
        Stream<String> init = init();
        //Optional<String> first = init.findFirst();
        Optional<String> first = init.filter(name -> name.startsWith("z")).findFirst();
        System.out.println(first);
    }

    /**
     * 查询是否含有指定的值,返回boolean
     * allMatch全部符合该条件返回true，
     * 	noneMatch全部不符合该断言返回true
     * 	anyMatch 任意一个元素符合该断言返回true
     */
    @Test
    public void testMatch() {
        Stream<String> init = init();
        boolean first = init.anyMatch(name -> name.startsWith("z2"));
        System.out.println(first);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        boolean allMatch = list.stream().allMatch(e -> e > 10); //false
        boolean noneMatch = list.stream().noneMatch(e -> e > 10); //true
        boolean anyMatch = list.stream().anyMatch(e -> e > 4); //true
        System.out.println(allMatch);
        System.out.println(noneMatch);
        System.out.println(anyMatch);
    }

    /**
     * 取对应的最大或最小值（聚合）
     */
    @Test
    public void testMaxOrMin() {
        Stream<String> init = init();
        Optional<String> max = init.max(Comparator.comparing(String::length));
        //Optional<String> min = init1.min(Comparator.comparing(String::length));
        System.out.println(max.get());
        //System.out.println(min.get());
    }

    /**
     * 规约。根据条件取值
     */
    @Test
    public void testReduce() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 8);
        List<String> string = Arrays.asList("1", "2", "3", "5", "6", "8");
        //求和
        Optional<Integer> reduce = integers.stream().reduce((x, y) -> x + y);
        //拼接
        String reduce5 = string.stream().reduce((x, y) -> x + "#" + y).get();
        //求和
        Optional<Integer> reduce1 = integers.stream().reduce(Integer::sum);
        //求和
        Integer reduce2 = integers.stream().reduce(0, Integer::sum);
        //求乘积
        Optional<Integer> reduce3 = integers.stream().reduce((x, y) -> x * y);
        //求最大值
        Optional<Integer> reduce4 = integers.stream().reduce(Integer::max);
        System.out.println(reduce.get());
        System.out.println(reduce1.get());
        System.out.println(reduce2);
        System.out.println(reduce3.get());
        System.out.println(reduce4.get());
        System.out.println(reduce5);
    }

    /**
     * 排序
     */
    @Test
    public void testSort() {
        List<Integer> integers = Arrays.asList(1, 2, 7, 4, 5, 6, 8);
        List<Integer> sorted = integers.stream().sorted().collect(Collectors.toList());
        List<UserEntity> list = initUser();
        //通过某个字段进行排序正序
        List<UserEntity> collect = list.stream().sorted(Comparator.comparingInt(UserEntity::getAge)).collect(Collectors.toList());
        //倒序
        List<UserEntity> collect1 = list.stream().sorted(Comparator.comparingInt(UserEntity::getAge).reversed()).collect(Collectors.toList());
        System.out.println(sorted);
        collect.forEach(System.out::println);
        collect1.forEach(System.out::println);
    }


    /**
     * 转换成map
     */
    @Test
    public void testCollect () {
        List<UserEntity> list = initUser();
        List<Integer> integers = Arrays.asList(1, 2, 3,3, 4, 5, 6, 8);
        //转换成map
        Map<String, UserEntity> collect = list.stream().collect(Collectors.toMap(UserEntity::getUserName, p -> p));
        Set<String> strings = collect.keySet();
        for (String s : strings) {
            UserEntity userEntity = collect.get(s);
            System.out.println(s+"===="+userEntity);
        }
        Map<String, Integer> collects = list.stream().collect(Collectors.toMap(UserEntity::getUserName, UserEntity::getAge));
        Set<String> string1 = collects.keySet();
        for (String s : string1) {
            Integer userEntity = collects.get(s);
            System.out.println(s+"===="+userEntity);
        }
        //分组收集
        Map<String, List<UserEntity>> collect2 = list.stream().collect(Collectors.groupingBy(UserEntity::getPassword));
        System.out.println(collect2);
        Set<String> strings1 = collect2.keySet();
        for (String s : strings1) {
            List<UserEntity> userEntity = collect2.get(s);
            System.out.println(s+"===="+userEntity);
        }
        //映射后分组
        Map<String, List<String>> collect3 = list.stream().collect(Collectors.groupingBy(UserEntity::getPassword, Collectors.mapping(UserEntity::getUserName, Collectors.toList())));
        System.out.println(collect3);
        Set<String> strings2 = collect3.keySet();
        for (String s : strings2) {
            List<String> userEntity = collect3.get(s);
            System.out.println(s+"===="+userEntity);
        }

        Set<Integer> collect1 = integers.stream().collect(Collectors.toSet());
        System.out.println(collect1);

    }

    @Test
    public void testList() {
        List<UserEntity> list = new ArrayList<>();
        UserEntity userEntity = new UserEntity();
        list.add(userEntity);
        System.out.println(list);
    }

    @Test
    public void testObjectToList() {
        List<UserEntity> list = initUser();
        UserEntity userEntity = UserEntity.builder()
                .userName("ss")
                .age(10)
                .password("123")
                .build();
        System.out.println(userEntity);
            Collection<UserEntity> userEntities = ObjectToListUtils.toCollection(userEntity);
        Collection<List<UserEntity>> lists = ObjectToListUtils.toCollection(list);
        System.out.println(userEntities);
        System.out.println(lists);
    }

    /**
     * 将多个集合合并
     */
    @Test
    public void testConcat() {
        List<String> strings = Arrays.asList("1", "88");
        List<String> strings1 = Arrays.asList("sd", "ds");
        List<String> collect = Stream.concat(strings1.stream(), strings.stream()).collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void testArrayToList() {
        int[] ints = {1, 2,7, 3, 5, 8, 9};
        List<Integer> collect = Arrays.stream(ints).boxed().collect(Collectors.toList());
        int asInt = Arrays.stream(ints).max().getAsInt();
        collect.forEach(System.out::println);
        System.out.println(asInt);


    }

    @Test
    public void test() {
        List<UserEntity> list = initUser();
        list.forEach(System.out::println);
        double sum = list.stream().mapToDouble(UserEntity::getAge).summaryStatistics().getSum();
        System.out.println(sum);

    }

    @Test
    public void test2() {
        Set<Integer> integers = new HashSet<>();
        integers.add(1);
        integers.add(2);
        Set<Integer> integers1 = new HashSet<>();
        //integers1.add(3);
        //integers1.add(2);
        integers.removeAll(integers1);
        System.out.println(integers);
    }

}
