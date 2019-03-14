package com.jenkins.own.utils;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {

    /**
     * 验证码校验
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request,String key) {
        //获取生成的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        if(key == null ||verifyCodeExpected==null||!verifyCodeExpected.equals(key)) {
            return false;
        }
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        return true;
    }
}