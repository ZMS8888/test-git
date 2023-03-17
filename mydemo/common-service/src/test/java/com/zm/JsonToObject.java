package com.zm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zm.entity.DTO.JsonToObjectDTO;
import com.zm.entity.MessageContent;
import com.zm.entity.UserEntity;
import org.junit.Test;

import java.util.*;

public class JsonToObject {

    String text = "[{\"autoIncrementConfigureType\":\"\",\"code\":5,\"example\":\"PR\",\"name\":\"固定信息\",\"rule\":\"PR\",\"uuid\":\"\"},{\"autoIncrementConfigureType\":\"\",\"code\":2,\"example\":\"20221018\",\"name\":\"当前日期\",\"rule\":\"yyyyMMdd\",\"uuid\":\"\"},{\"autoIncrementConfigureType\":\"day\",\"code\":4,\"example\":\"001\",\"name\":\"自动生成序号\",\"rule\":\"3\",\"uuid\":\"6d21e488-bc06-4d02-9efb-0ae2a75cb1d3\"}]";
    String object = "{\"autoIncrementConfigureType\":\"\",\"code\":5,\"example\":\"PR\",\"name\":\"固定信息\",\"rule\":\"PR\",\"uuid\":\"\"}";

    /**
     * json转换成对象集合
     */
    @Test
    public void jsonToObjectList() {
        List<JsonToObjectDTO> jsonToObjectDTOS = JSON.parseArray(text, JsonToObjectDTO.class);
        jsonToObjectDTOS.forEach(jsonToObjectDTO -> {
            System.out.println(jsonToObjectDTO);
        });
    }

    /**
     * json转换成对象
     */
    @Test
    public void jsonToObject() {
        JsonToObjectDTO jsonToObjectDTO1 = JSON.parseObject(object, JsonToObjectDTO.class);
        System.out.println(jsonToObjectDTO1);
    }

    /**
     * 对象转换成json
     */
    @Test
    public void objectToJson() {
        UserEntity init = init();
        String string = JSON.toJSONString(init);
        UserEntity userEntity = JSON.parseObject(string, UserEntity.class);
        System.out.println(init);
        System.out.println(string);
        System.out.println(userEntity);

    }
    /**
     * 对象转换成json
     */
    @Test
    public void objectToJsons() {
        List<UserEntity> init = inits();
        String string = JSON.toJSONString(init);
        List<UserEntity> userEntity = JSON.parseArray(string, UserEntity.class);
        System.out.println(init);
        System.out.println(string);
        System.out.println(userEntity);


    }

    private UserEntity init() {
        UserEntity userEntity1 = UserEntity.builder()
                .age(8)
                .userName("ss")
                .password("123")
                .build();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("zm");
        userEntity.setAge(10);
        userEntity.setPassword("123");
        return userEntity;
    }

    private List<UserEntity> inits() {
        UserEntity userEntity1 = UserEntity.builder()
                .age(8)
                .userName("ss")
                .password("123")
                .build();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("zm");
        userEntity.setAge(10);
        userEntity.setPassword("123");
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity1);
        userEntities.add(userEntity);
        return userEntities;
    }

    @Test
    public  void testO() {
        Map<String, Object> hashMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("bbbbb");
        hashMap.put("message", "你好");
        hashMap.put("urlS", list);
        MessageContent message = MessageContent.builder().message(hashMap).time(new Date()).build();
        System.out.println(message);
        JSONObject map = new JSONObject();
        map.put("content", hashMap);
        map.put("topic", "TOPIC");
        String string = map.toJSONString();
        String string1 = JSONObject.toJSONString(map);
        System.out.println(string);
        System.out.println(string1);

    }
}
