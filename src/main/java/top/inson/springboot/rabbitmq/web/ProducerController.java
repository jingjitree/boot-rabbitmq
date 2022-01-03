package top.inson.springboot.rabbitmq.web;

import ch.qos.logback.core.util.DatePatternToRegexUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.inson.springboot.rabbitmq.constant.MQConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jingjitree
 * @version 1.0
 * @className ProducerController
 * @description
 * @date 2022/1/3 17:22
 **/
@RestController
@RequestMapping(value = "/producer")
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/sendMsg")
    public String sendMsg(@RequestParam String msg){
        String msgId = RandomUtil.randomString(32);
        Map<String, Object> messageObj = MapUtil.builder(new HashMap<String, Object>())
                .put("msgId", msgId)
                .put("messageData", msg)
                .put("createTime", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_MS_PATTERN))
                .build();
        rabbitTemplate.convertAndSend(MQConstant.DIRECT_EXCHANGE_NAME, MQConstant.ROUTING_KEY_NAME, messageObj, new CorrelationData(msgId));

        return "success";
    }


}
