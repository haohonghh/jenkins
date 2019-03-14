package com.jenkins.own.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.jenkins.own.commom.Assert;
import com.jenkins.own.dao.UserMapper;
import com.jenkins.own.entity.User;
import com.jenkins.own.exception.RRException;
import com.jenkins.own.service.UserService;
import com.jenkins.own.utils.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public Map login(User user) {
        Wrapper<User> wrapper = new QueryWrapper<>();
        ((QueryWrapper<User>) wrapper).eq("email",user.getEmail());
        User queryUser = this.getOne(wrapper);
        Assert.isNull(queryUser, "邮箱不存在！");
        ((QueryWrapper<User>) wrapper).eq("password",user.getPassword());
        User isRight = this.getOne(wrapper);
        Assert.isNull(isRight, "邮箱或密码错误！");
        Long userId = isRight.getId();

        String token = jwtUtils.generateToken(userId);
        Long expire = jwtUtils.getExpire();
        Map map = new HashMap();
        map.put("token",token);
        map.put("expire",expire);
        return map;
    }

    @Override
    public Map regist(User user) {
        Map map = new HashMap();
        Wrapper<User> wrapper = new QueryWrapper<>();
        ((QueryWrapper<User>) wrapper).eq("email",user.getEmail());
        User queryUser = this.getOne(wrapper);
        if (queryUser==null){
            boolean flag = this.save(user);
            if (flag){
                map.put("flag",flag);
                map.put("code",200);
                map.put("msg","success");
            }else{
                map.put("flag",flag);
                map.put("code",0);
                map.put("msg","error");
            }
        }else {
            map.put("flag",false);
            map.put("code",0);
            map.put("msg","邮箱已存在");
        }

        return map;
    }
}
