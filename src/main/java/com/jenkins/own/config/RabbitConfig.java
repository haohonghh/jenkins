package com.jenkins.own.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author: YeFei
 * @Date: Created in 15:03 2019/2/13
 * @Description:
 */
@Configuration
@Slf4j
@Data
public class RabbitConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private String port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.connection-timeout}")
    private String connectionTimeout;
    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;
    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean publisherReturns;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host + ":" + port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setConnectionTimeout(Integer.parseInt(connectionTimeout
                .substring(0, connectionTimeout.length()-2)));
        // 如果要进行消息回调,则这里必须要设置为true
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(publisherReturns);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMandatory(true);
        // TODO 相应交换机接收后异步回调  关闭了回调功能, 此处暂时不知道什么原因,如果开启后就变成同步了
        /*rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("交换机接收信息成功,id:{}", correlationData.getId());
            } else {
                log.error("交换机接收信息失败:{}", cause);
            }
        });
        无相应队列与交换机绑定异步回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String msg = new String(message.getBody());
            log.error("消息:{} 发送失败, 应答码:{} 原因:{} 交换机:{} 路由键:{}", msg, replyCode, replyText, exchange, routingKey);
        });*/
        return rabbitTemplate;
    }
}

