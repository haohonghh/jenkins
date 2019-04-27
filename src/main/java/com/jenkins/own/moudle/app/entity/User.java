package com.jenkins.own.moudle.app.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String name;

    private Integer age;

    @NotNull(message="邮箱不能为空")
    @Email
    private String email;

    @NotNull(message="密码不能为空")
    private String  password;
}