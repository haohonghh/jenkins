package com.jenkins.own.moudle.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jenkins.own.moudle.app.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {


     Map login(User user);

     Map regist(User user);

     List<User> getList(int pageNum, int pageSize);
}
