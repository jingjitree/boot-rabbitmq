package top.inson.springboot.rabbitmq.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author jingjitree
 * @version 1.0
 * @className MQConstant
 * @description
 * @date 2022/1/3 17:09
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MQConstant {

    public static final String QUEUE_NAME = "tree_queue";
    public static final String DIRECT_EXCHANGE_NAME = "tree_direct_exchange";
    public static final String ROUTING_KEY_NAME = "tree_routing";



}
