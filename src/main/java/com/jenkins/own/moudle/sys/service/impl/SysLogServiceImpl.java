
package com.jenkins.own.moudle.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jenkins.own.moudle.sys.dao.SysLogDao;
import com.jenkins.own.moudle.sys.entity.SysLogEntity;
import com.jenkins.own.moudle.sys.service.SysLogService;
import org.springframework.stereotype.Service;

@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {


}
