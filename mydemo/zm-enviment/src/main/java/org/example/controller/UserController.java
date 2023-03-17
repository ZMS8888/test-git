package org.example.controller;

import org.example.entity.SysUserEntity;
import org.example.pojo.ResponseData;
import org.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    public String list() {
        return "Hello word123";
    }

    @GetMapping("/get/users")
    @Cacheable(value ="yl_user", key = "'yl_00001'")  //key值需要用单引号
    public ResponseData getUser() {
        List<SysUserEntity> list = sysUserService.list();
        redisTemplate.opsForValue().set("yl_user01","hewew");
        return ResponseData.success(list);
    }


    @GetMapping("/get/cache")
    public ResponseData getCache() {
        Object o = redisTemplate.opsForValue().get("yl_user::yl_00001");
        Object o1 = redisTemplate.opsForValue().get("yl_user01");
        return ResponseData.success(o1);
    }







}
