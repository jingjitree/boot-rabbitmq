package top.inson.springboot.rabbitmq.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloWorldController
 * @Description
 * @Author jingjitree
 * @Date 2020/11/4 11:52
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping(value = "/helloWorld")
public class HelloWorldController {

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam String username){
        log.info("说hello接口进来了，看看有没有进程id");
        log.info("接口请求参数username：" + username);

        return username + "你好世界！";
    }


}
