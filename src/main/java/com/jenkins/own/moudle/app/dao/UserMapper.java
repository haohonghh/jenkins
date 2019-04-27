package com.jenkins.own.moudle.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jenkins.own.moudle.app.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
        List<User> getList(int pageNum,int pageSize);
}