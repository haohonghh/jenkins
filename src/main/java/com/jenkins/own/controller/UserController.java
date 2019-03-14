package com.jenkins.own.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IOUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.jenkins.own.anno.Login;
import com.jenkins.own.commom.Assert;
import com.jenkins.own.entity.Mail;
import com.jenkins.own.entity.User;
import com.jenkins.own.exception.RRException;
import com.jenkins.own.rabbit.RabbitConstant;
import com.jenkins.own.service.UserService;
import com.jenkins.own.utils.CodeUtil;
import com.jenkins.own.utils.MailUtil;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletRequest request,HttpServletResponse response, String uuid)throws IOException {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取验证码
        String code = producer.createText();
        BufferedImage image = producer.createImage(code);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    //对象转化为字节码
    public  byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }



    @PostMapping("/regist")
    public Map regist(@RequestBody @Valid User user,String code,HttpServletRequest request) throws Exception {
        Map map = new HashMap();
        if (CodeUtil.checkVerifyCode(request,code)){
           map =  userService.regist(user);
           byte[] bytes=getBytesFromObject(user);
           rabbitTemplate.convertAndSend(RabbitConstant.CONTROL_EXCHANGE, RabbitConstant.EMAIL_ROUTING_KEY,bytes);
        }else {
            map.put("flag",false);
            map.put("code",0);
            map.put("msg","验证码不正确");
        }
        return  map;
    }



    @PostMapping("/login")
    public Map login(@RequestBody @Valid User user,String code,HttpServletRequest request){
        Map map = new HashMap();
        if (CodeUtil.checkVerifyCode(request,code)){
            map = userService.login(user);
        }else {
            map.put("flag",false);
            map.put("code",0);
            map.put("msg","验证码不正确");
        }
        return  map;
    }



    @Login
    @GetMapping("/userInfo/{id}")
    public User getUserInfoById(@PathVariable Long id){
        return userService.getById(id);
    }
}
