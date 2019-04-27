package com.jenkins.own.simpleIOC;

import lombok.Data;

@Data
public class Car {
    private String name;
    private String length;
    private String width;
    private String height;
    private Wheel wheel;

}

