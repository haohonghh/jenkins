package com.jenkins.own.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class FanoutRabbitConfig {
    //创建队列
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }
    //创建队列
    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }
    //创建队列
    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }
    //创建Fanout交换器
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConstant.FANOUT_ROUTING_KEY);
    }
    //将对列绑定到Fanout交换器
    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }
    //将对列绑定到Fanout交换器
    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }
    //将对列绑定到Fanout交换器
    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }    
}