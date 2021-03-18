package sc.hqu.graduationdesign.homeworkmanager.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.RocketMqServiceTest;
import sc.hqu.graduationdesign.homeworkmanager.model.NotificationMessage;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;

/**
 * @author tzx
 * @date 2021-03-18 1:09
 */
@RestController
public class TestController {

    @Autowired
    private RocketMqServiceTest serviceTest;


    @GetMapping(value = "/send")
    public String send(){
        serviceTest.publishMessage();
        return "success!";
    }

    @Autowired
    private GenericCacheProvider cacheProvider;

    @GetMapping(value = "/redis")
    public Object redisTest(){
        NotificationMessage message = new NotificationMessage();
        message.setType(1);
        message.setTitle("redis");
        message.setContent("娃娃啊哇哇哇哇");
        message.setConfirmable(false);
        message.setPublishDate(System.currentTimeMillis());
        cacheProvider.setIfAbsent("test", message);
        return cacheProvider.get("test");
    }

}
