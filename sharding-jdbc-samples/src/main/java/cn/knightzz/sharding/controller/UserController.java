package cn.knightzz.sharding.controller;

import cn.knightzz.sharding.entity.User;
import cn.knightzz.sharding.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    public String getUser(){
        List<User> users = userMapper.selectList(null);
        return "success";
    }
}

