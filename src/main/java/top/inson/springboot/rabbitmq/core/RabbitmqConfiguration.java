package top.inson.springboot.rabbitmq.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.inson.springboot.rabbitmq.constant.MQConstant;

import java.text.MessageFormat;

/**
 * @className RabbitmqConfiguration
 * @description
 * @author jingjitree
 * @date 2022/1/3 16:41
 * @version 1.0
 **/
@Slf4j
@Configuration
public class RabbitmqConfiguration {

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback((data, ack, cause) -> {
            String msgId = data.getId();

            if (ack){
                log.info("msgId:{},消息确认", msgId);
            }else {
                log.info("msgId:{},消息发送失败", msgId);
            }
        });

        // confirm 模式只能保证消息到达 broker，不能保证消息准确投递到目标 queue 里。
        // 在有些业务场景下，我们需要保证消息一定要投递到目标 queue 里，此时就需要用到 return 退回模式
        // 这样如果未能投递到目标 queue 里将调用 returnCallback，可以记录下详细到投递数据，定期的巡检或者自动纠错都需要这些数据
        template.setReturnsCallback(returned -> {

            log.info("消息发送失败msg");

        });
        return template;
    }


    @Bean
    Queue queue(){

        return new Queue(MQConstant.QUEUE_NAME, true);
    }

    @Bean
    DirectExchange directExchange(){

        return new DirectExchange(MQConstant.DIRECT_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding binding(){

        return BindingBuilder.bind(queue()).to(directExchange()).with(MQConstant.ROUTING_KEY_NAME);
    }

}
