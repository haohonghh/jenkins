package com.jenkins.own.wechart.token;


import lombok.Data;

@Data
public class Token {


    private String accessToken;

    private int expiresIn;
}
