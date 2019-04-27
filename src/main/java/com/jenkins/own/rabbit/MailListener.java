package com.jenkins.own.rabbit;

import com.alibaba.fastjson.JSON;
import com.jenkins.own.moudle.app.entity.User;
import com.jenkins.own.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

@Component
@Slf4j
public class MailListener {
    @Autowired
    private MailUtil mailUtil;

    /**
     * 监听mq队列的消息
     * @param
     */
    @RabbitListener(queues = RabbitConstant.EMAIL_QUEUE)
    public void processEmail(byte[] objBytes) {
        try {
            User user=(User) getObjectFromBytes(objBytes);
            log.info("准备开始向"+user.getName()+"发送邮件......");
         /*   Mail mail = new Mail();
            mail.setSubject("test");
            mail.setToWho(user.getEmail());
            mail.setText("恭喜你："+user.getName()+"注册成功");*/
         System.out.println(JSON.toJSONString(user));
            mailUtil.sendMail();
            log.info("发送邮件成功......");
        } catch (Exception e) {
            log.error("邮件发送失败了!");
        }
    }


    //字节码转化为对象
    public  Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }
}
