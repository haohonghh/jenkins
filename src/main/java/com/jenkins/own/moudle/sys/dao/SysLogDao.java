package com.jenkins.own.moudle.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jenkins.own.moudle.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
