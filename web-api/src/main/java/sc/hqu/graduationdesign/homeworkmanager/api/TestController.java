package sc.hqu.graduationdesign.homeworkmanager.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.RocketMqServiceTest;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.FileUploadException;
import sc.hqu.graduationdesign.homeworkmanager.model.NotificationMessage;
import sc.hqu.graduationdesign.homeworkmanager.provider.FtpServiceProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericFileServiceProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

/**
 * @author tzx
 * @date 2021-03-18 1:09
 */
@RestController
public class TestController {

    @Autowired
    private RocketMqServiceTest serviceTest;

    @Autowired
    private GenericFileServiceProvider ftpServiceProvider;


    @GetMapping(value = "/test/send")
    public String send(){
        serviceTest.publishMessage();
        return "success!";
    }

    @Autowired
    private GenericCacheProvider cacheProvider;

    @GetMapping(value = "/test/redis")
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


    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping(value = "/test/upload")
    public String uploadTest(@RequestParam("file") MultipartFile file){
        if (file != null){
            try {
                return ftpServiceProvider.upload(file);
            } catch (FileUploadException e) {
                e.printStackTrace();
                return "fail to upload";
            }
        }
        return "file is null";
    }

}
