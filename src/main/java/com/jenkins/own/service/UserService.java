package com.jenkins.own.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jenkins.own.entity.User;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface UserService extends IService<User> {


     Map login(User user);

     Map regist(User user);
}
