package com.zm;

import com.zm.entity.UserEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalTest {
    private static List<UserEntity> initUser() {
        List<UserEntity> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UserEntity userEntity;
            if (i / 2 == 0) {
                userEntity = UserEntity.builder()
                        .age(i)
                        .userName("zm" + i)
                        .password("123456" + i)
                        .build();
            } else {
                userEntity = UserEntity.builder()
                        .age(i)
                        .userName("zm" + i)
                        .password("123456")
                        .build();
            }
            list.add(userEntity);

        }
        return list;
    }

    @Test
    public void test() {
        List<UserEntity> list = initUser();
        UserEntity userEntity = new UserEntity();
        userEntity = null;
        list.add(userEntity);
        Optional<UserEntity> userEntity1 = Optional.ofNullable(userEntity);
        //判断是否为null，空返回FALSE，否则TRUE
        System.out.println(userEntity1.isPresent());
        Optional.ofNullable(list).ifPresent(s->{
            System.out.println(s);
        });
        System.out.println(userEntity1);

        Optional<Object> o = Optional.of(null);
        System.out.println(o);
    }
}
