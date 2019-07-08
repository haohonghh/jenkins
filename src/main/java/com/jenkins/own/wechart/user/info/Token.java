package com.jenkins.own.wechart.user.info;

import lombok.Data;

/**
* 类名: Token </br>
* 描述:  凭证  </br>
* 开发人员： souvc </br>
* 创建时间：  2015-11-27 </br>
* 发布版本：V1.0  </br>
 */
@Data
public class Token {
    // 接口访问凭证
    private String accessToken;

    // 凭证有效期，单位：秒
    private int expiresIn;

}