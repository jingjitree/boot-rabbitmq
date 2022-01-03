package top.inson.springboot.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import top.inson.springboot.rabbitmq.constant.MQConstant;

import java.io.IOException;

/**
 * @author jingjitree
 * @version 1.0
 * @className ConsumerListener
 * @description
 * @date 2022/1/3 17:58
 **/
@Slf4j
@Component
public class ConsumerListener {

    @SneakyThrows(IOException.class)
    @RabbitListener(queues = MQConstant.QUEUE_NAME)
    public void queueListener(Message message, Channel channel) {
        log.info("监听到消息内容：" + message.toString());
        MessageHeaders headers = message.getHeaders();
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        log.info("tag:" + tag);
        try {
            channel.basicAck(tag, false);
        }catch (IOException e){
            //消费失败的消息重新放入队列
            channel.basicNack(tag, false, true);
        }

    }


}
