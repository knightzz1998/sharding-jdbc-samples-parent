package cn.knightzz.sharding.controller;

import cn.knightzz.sharding.entity.User;
import cn.knightzz.sharding.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/api/write")
    public String write(User user){
        userMapper.insert(user);
        return "success";
    }

    @GetMapping("/api/read")
    public List<User> getUser(){
        return userMapper.selectList(null);
    }
}

