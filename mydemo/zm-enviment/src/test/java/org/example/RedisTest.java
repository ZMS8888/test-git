package org.example;


import org.example.entity.UserVO;
import org.example.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnvironmentApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void test() throws InterruptedException {
        redisTemplate.opsForValue().set("yl_01::00", "hello word", 60, TimeUnit.SECONDS);
        //System.out.println(redisTemplate.opsForValue().get("yl_01"));
        //Thread.sleep(5000);
        //System.out.println(redisTemplate.opsForValue().get("yl_01"));
    }

    @Test
    public void test3() {
        System.out.println(redisTemplate.opsForValue().get("yl_user::yl_00001"));
    }

    @Test
    public void test1() {

    }

    private HashMap<String, UserVO> initUser() {
        HashMap<String, UserVO> userVOS = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            UserVO userVO = UserVO.builder().
                    age(i)
                    .name("zz" + i).build();
            userVOS.put(String.valueOf(i), userVO);
        }
        return userVOS;
    }

    @Test
    public void deleteRedis() {
        Boolean yl_user01 = redisTemplate.delete("yl_user01");
        System.out.println(yl_user01);
    }

    /**
     * https://blog.csdn.net/yucaifu1989/article/details/124631785
     */
    @Test
    public void testString() {
        ////设置当前的key以及value值
        redisTemplate.opsForValue().set("yl_user01", "hello");
        System.out.println(redisTemplate.opsForValue().get("yl_user01"));
        //用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
        redisTemplate.opsForValue().set("yl_user01", "hello", 4);
        System.out.println(redisTemplate.opsForValue().get("yl_user01"));
        System.out.println(redisTemplate.opsForValue().get("yl_user01", 2, 5));
        //重新设置key对应的值，如果存在返回false，否则返回true
        redisTemplate.opsForValue().setIfAbsent("yl_user01", "hello");
        System.out.println(redisTemplate.opsForValue().get("yl_user01"));
        //设置当前的key以及value值并且设置过期时间
        redisTemplate.opsForValue().set("yl_user02", "word", 60, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("yl_user02"));
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("yl_user03", "1");
        hashMap.put("yl_user04", "2");
        hashMap.put("yl_user05", "3");
        //以map集合的形式插入一个key-value对应Redis的key-value
        redisTemplate.opsForValue().multiSet(hashMap);
        Set<Map.Entry<String, Object>> entries = hashMap.entrySet();
        Collection collection = new ArrayList<>();
        entries.forEach(stringObjectEntry -> {
            collection.add(stringObjectEntry.getKey());
        });
        System.out.println(redisTemplate.opsForValue().multiGet(collection));
        ////在原有的值基础上新增字符串到末尾
        redisTemplate.opsForValue().append("yl_user01", "111");
        //以增量的方式将double值存储在变量中
        redisTemplate.opsForValue().increment("yl_user01", 2);
        //通过increment(K key, long delta)方法以增量方式存储long值（正值则自增，负值则自减）
        redisTemplate.opsForValue().increment("yl_user01", 2);

    }

    @Test
    public void testHash() {
        HashMap<String, UserVO> integerUserVOHashMap = initUser();
        //以map集合的形式添加键值对
        redisTemplate.opsForHash().putAll("yl_hash_1", integerUserVOHashMap);
        //根据Redis的key和map的key获得对应的value
        System.out.println(redisTemplate.opsForHash().get("yl_hash", "1"));
        //新增hash值
        //redisTemplate.opsForHash().put("yl_hash", "5",2);
        //新增hash值，当map的key不存在时插入
        //redisTemplate.opsForHash().put("yl_hash", "4","5");
        //通过Redis的key值获得对应的map
        System.out.println(redisTemplate.opsForHash().entries("yl_hash"));
        //通过Redis的key和map的key删除对应的map
        //redisTemplate.opsForHash().delete("yl_hash", "1");
        //查看hash表中指定字段是否存在
        Boolean yl_hash = redisTemplate.opsForHash().hasKey("yl_hash", "1");
        System.out.println(yl_hash);
        //通过两个key累加
        //redisTemplate.opsForHash().increment("yl_hash", "5", 1);
        //通过key获得hash的key值
        System.out.println(redisTemplate.opsForHash().keys("yl_hash"));
        //通过key获得hash的value值
        System.out.println(redisTemplate.opsForHash().values("yl_hash"));
    }

    @Test
    public void testList() {
        //存储在list的头部，即添加一个就把它放在最前面的索引处
        //redisTemplate.opsForList().leftPush("yl_list01","15");
        //如果pivot处值存在则在pivot前面添加
        //redisTemplate.opsForList().leftPush("yl_list01", 2, "16");
        //把多个值存入List中(value可以是多个值，也可以是一个Collection value)
        //redisTemplate.opsForList().leftPushAll("yl_list01","78","87");
        //通过索引获取列表中的元素
        System.out.println(redisTemplate.opsForList().index("yl_list01", 2));
        ////获取列表指定范围内的元素(start开始位置, 0是开始位置，end 结束位置, -1返回所有)
        System.out.println(redisTemplate.opsForList().range("yl_list01", 0, 3));
        //设置指定索引处元素的值
        //redisTemplate.opsForList().set("yl_list01",2,"22");
        //将List列表进行剪裁,删除不在范围能的数据
        //redisTemplate.opsForList().trim("yl_list01",0,1);
        //获取当前key的List列表长度
        System.out.println(redisTemplate.opsForList().size("yl_list01"));
        //移除并获取列表中第一个元素(如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止)
        System.out.println(redisTemplate.opsForList().leftPop("yl_list01"));
        //删除集合中值等于value的元素(index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素; index<0, 从尾部开始删除第一个值等于value的元素)
        redisTemplate.opsForList().remove("yl_list01", 1, "78");
        //从一个队列的右边弹出一个元素并将这个元素放入另一个指定队列的最左边
        redisTemplate.opsForList().rightPopAndLeftPush("yl_list01", "yl_list02");
    }

    private static final String BASE_DATA_BATCH_IMPORT_PROGRESS = "BASE_DATA_BATCH_IMPORT_PROGRESS";
    private static final String UNIT = "UNIT";
    private static final String TYPE = "TYPE";

    @Test
    public void testGetProgress() throws InterruptedException {
     /*   for (int i = 0; i < 10; i++) {
            redisTemplate.opsForHash().put(BASE_DATA_BATCH_IMPORT_PROGRESS, UNIT,"插入+"+i+"+条");
            redisTemplate.opsForHash().put(BASE_DATA_BATCH_IMPORT_PROGRESS, TYPE,"插入"+i+"条");
            Thread.sleep(2000);
            System.out.println(redisTemplate.opsForHash().get(BASE_DATA_BATCH_IMPORT_PROGRESS,UNIT));
            System.out.println(redisTemplate.opsForHash().get(BASE_DATA_BATCH_IMPORT_PROGRESS,TYPE));
        }*/
        //redisTemplate.opsForSet().add("yl_set02", "112", "12,", "13");
        List values = redisTemplate.opsForHash().values(BASE_DATA_BATCH_IMPORT_PROGRESS);
        Map entries = redisTemplate.opsForHash().entries(BASE_DATA_BATCH_IMPORT_PROGRESS);
        System.out.println(values);
        System.out.println(entries);
    }


    @Test
    public void testSet() {
        //添加元素
        //redisTemplate.opsForSet().add("yl_set02", "112", "12,", "13");
        ////移除元素(单个值、多个值)，返回移除个数
        //System.out.println(redisTemplate.opsForSet().remove("yl_set01", "2,", "11"));
        //删除并且返回一个随机的元素
        //System.out.println(redisTemplate.opsForSet().pop("yl_set01"));
        //获取集合大小
        //System.out.println(redisTemplate.opsForSet().size("yl_set01"));
        //判断集合是否包含value
        //System.out.println(redisTemplate.opsForSet().isMember("yl_set01","11"));
        //获取多个集合的交集
        //System.out.println(redisTemplate.opsForSet().intersect("yl_set01","yl_set02"));
        //获取多个集合的交集,存储到第三个集合里
        //System.out.println(redisTemplate.opsForSet().intersectAndStore("yl_set01","yl_set02","yl_set03"));
        //获取多个集合的并集
        System.out.println(redisTemplate.opsForSet().union("yl_set01", "yl_set02"));
        List<String> strings = Arrays.asList("yl_set02", "yl_set03");
        System.out.println(redisTemplate.opsForSet().union("yl_set01", strings));
        ////key集合与otherKey集合的并集存储到destKey中(otherKeys可以为单个值或者是集合)
        //redisTemplate.opsForSet().unionAndStore("yl_set01", strings, "yl_set04");
        ////获取两个或者多个集合的差集(otherKeys可以为单个值或者是集合)——》A与B差集：属于A但不属于B的集合
        System.out.println(redisTemplate.opsForSet().difference("yl_set03", "yl_set02"));
        //差集存储到destKey中(otherKeys可以为单个值或者集合)
        //redisTemplate.opsForSet().differenceAndStore("yl_set03", "yl_set02","yl_set05");
        //随机获取集合中的一个元素
        System.out.println(redisTemplate.opsForSet().randomMember("yl_set02"));
        //随机获取集合中指定元素
        System.out.println(redisTemplate.opsForSet().randomMembers("yl_set02", 2));
        //获取集合中的所有元素
        System.out.println(redisTemplate.opsForSet().members("yl_set02"));
        //遍历set类似于Interator(ScanOptions.NONE为显示所有的)
        Cursor yl_set02 = redisTemplate.opsForSet().scan("yl_set02", ScanOptions.NONE);
        while (yl_set02.hasNext()) {
            System.out.println(yl_set02.next());
        }
    }

    @Test
    public void testZSet() {
        //添加元素(有序集合是按照元素的score值由小到大进行排列)
        //redisTemplate.opsForZSet().add("yl_zset01", "1", 1);
        //redisTemplate.opsForZSet().add("yl_zset01", "3", 2);
        //redisTemplate.opsForZSet().add("yl_zset01", "2", 3);
        //redisTemplate.opsForZSet().add("yl_zset01", "12", 13);
        //删除对应的value,value可以为多个值,返回删除元素的个数
        System.out.println(redisTemplate.opsForZSet().remove("yl_zset01", "2"));
        //增加元素的score值，并返回增加后的值
        //System.out.println(redisTemplate.opsForZSet().incrementScore("yl_zset01", "1", 13));
        //返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
        System.out.println(redisTemplate.opsForZSet().rank("yl_zset01", "12"));
        //返回指定范围内的集合有序集合是按照元素的score值由大到小排列
        System.out.println(redisTemplate.opsForZSet().reverseRange("yl_zset01", 0, 3));

        redisTemplate.opsForZSet().reverseRangeWithScores("yl_zset01", 0, 3);    //获取集合中给定区间的元素(start 开始位置，end 结束位置, -1查询所有)
        redisTemplate.opsForZSet().reverseRangeByScore("yl_zset01", 0, 3);    //按照Score值查询集合中的元素，结果从小到大排序
        redisTemplate.opsForZSet().reverseRangeByScoreWithScores("yl_zset01", 0, 3);    //返回值为:Set<ZSetOperations.TypedTuple<V>>
        redisTemplate.opsForZSet().count("yl_zset01", 0, 3);                //根据score值获取集合元素数量
        redisTemplate.opsForZSet().size("yl_zset01");                            //获取集合的大小
        redisTemplate.opsForZSet().zCard("yl_zset01");                            //获取集合的大小
        redisTemplate.opsForZSet().score("yl_zset01", "2");                    //获取集合中key、value元素对应的score值
        redisTemplate.opsForZSet().removeRange("yl_zset01", 0, 3);        //移除指定索引位置处的成员
        redisTemplate.opsForZSet().removeRangeByScore("yl_zset01", 0, 3);    //移除指定score范围的集合成员
        redisTemplate.opsForZSet().unionAndStore("yl_zset01", "yl_zset02", "yl_zset03");//获取key和otherKey的并集并存储在destKey中（其中otherKeys可以为单个字符串或者字符串集合）
        redisTemplate.opsForZSet().intersectAndStore("yl_zset01", "yl_zset02", "yl_zset03");    //获取key和otherKey的交集并存储在destKey中（其中otherKeys可以为单个字符串或者字符串集合）
    }


/*    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping("/get/service")
    public ResponseData  getNacosConfig() {
        List<List<ServiceInstance>> serviceInstances = new ArrayList<>();
        List<String> services = discoveryClient.getServices();
        for (String s : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(s);
            if (instances.size() > 0) {
                serviceInstances.add(instances);
            }
        }
        return ResponseData.success(serviceInstances);
    }*/

}
