package com.jenkins.own;

import com.alibaba.fastjson.JSON;
import com.jenkins.own.commom.R;
import com.jenkins.own.moudle.app.dao.UserMapper;
import com.jenkins.own.moudle.app.entity.Mail;
import com.jenkins.own.moudle.app.entity.User;
import com.jenkins.own.rabbit.RabbitConstant;
import com.jenkins.own.simpleIOC.Car;
import com.jenkins.own.simpleIOC.SimpleIOC;
import com.jenkins.own.simpleIOC.Wheel;
import com.jenkins.own.utils.JwtUtils;
import com.jenkins.own.utils.MailUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnJenkinsApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(1, userList.size());
        userList.forEach(System.out::println);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
    生产者
     */
    @Test
    public void saveUser() {
        User user = new User();
        user.setId(11L);
        user.setAge(12);
        user.setName("dsa");
        long l = System.currentTimeMillis();

        send(user.getId().toString(), RabbitConstant.CONTROL_EXCHANGE, RabbitConstant.EMAIL_ROUTING_KEY);
        System.out.println("mq消费时间: " + (System.currentTimeMillis() - l));
        int res = userMapper.insert(user);
        System.out.println("总消耗时间为:" + (System.currentTimeMillis() - l));

    }

    //发送信息
    private void send(String msg, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
        System.out.println("rabbitmq消息已经发送到交换机, 等待交换机接受..." + msg.toString());
    }


    /**
     * 消费者
     */
    /**
     * 监听mq队列的消息
     * @param msg
     */
    @RabbitListener(queues = RabbitConstant.EMAIL_QUEUE)
    public void processEmail(String[] msg) {
        try {
            System.out.println("准备开始发送邮件......" + msg.toString());
            sendTest();
        } catch (Exception e) {
            System.out.println("邮件发送失败了!");
        }
    }

    @Autowired
    private MailUtil util;


    public void attachEmail(Mail mailBean) {
        String filePath="C:\\Users\\aaaa\\Desktop\\mail.png";
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String attachmentFilename = filePath.substring(filePath.lastIndexOf(File.separator));
        mailBean.setSubject("SpringBoot集成JavaMail实现邮件发送");
        mailBean.setText("SpringBoot集成JavaMail实现附件邮件发送");
        mailBean.setFile(file);
        mailBean.setAttachmentFilename(attachmentFilename);
        util.sendMailAttachment(mailBean);
    }


    public void sendTest(){
        Mail mail = new Mail() ;
        mail.setSubject("登录验证");
        mail.setText(UUID.randomUUID().toString());
        util.sendMail(mail);
    }

    @Test
    public void jwtTest(){
       // System.out.println(jwtUtils.isTokenExpired(new Date(1553156286)));
        System.out.println(jwtUtils.generateToken(1L));
       // Claims claimByToken = jwtUtils.getClaimByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTIzIiwiaWF0IjoxNTUyNTUxNDg2LCJleHAiOjE1NTMxNTYyODZ9.39UvXnoZIFmqQzRBKHGYKhcc8IHfS4AWk7QBGzlOPDH8jIX4B5F8DgALA7FYFj3dVfGjFq0H6I_HRLZcrx-GMA");
      //  System.out.println(JSON.toJSONString(claimByToken));

    }

    @Test
    public void test(){
        R a = new R();
        System.out.println(a instanceof Object);

    }


    @Test
    public void getBean() throws Exception {
        String location = SimpleIOC.class.getClassLoader().getResource("ioc.xml").getFile();
        SimpleIOC bf = new SimpleIOC(location);
        Wheel wheel = (Wheel) bf.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) bf.getBean("car");
        System.out.println(car);
    }

    @Test
    public void test2() throws Exception {
        userMapper.getList(0,5);
    }


    @Test
    public void conn() throws Exception {
       Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf-8&useSSL=true","root","root");
        PreparedStatement preparedStatement = conn.prepareStatement("select  * from  user");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){

           System.out.println(resultSet.getLong("id"));
        }
        System.out.println(conn);
    }


    @Test
    public void maopao(){
        int[] arr = {1,10,8,7,11};
        System.out.println("排序前："+JSON.toJSONString(arr));
        for (int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if (arr[j]>arr[i]){
                    int tem = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tem;
                }
            }
        }

        System.out.println("排序后："+JSON.toJSONString(arr));
    }

    @Test
    public void erfenfa(){
        int[] arr = {1,2,4,7,11};
     //   System.out.println("序号为："+search(arr,1));
      //  System.out.println("序号为："+search(arr,2));
     //   System.out.println("序号为："+search(arr,4));
      //  System.out.println("序号为："+search(arr,7));
     //   System.out.println("序号为："+search(arr,11));

    }


    public static int search(int[] arr, int key) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (key < arr[middle]) {
                end = middle - 1;
            } else if (key > arr[middle]) {
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }
}

