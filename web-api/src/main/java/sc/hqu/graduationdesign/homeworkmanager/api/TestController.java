package sc.hqu.graduationdesign.homeworkmanager.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.FileService;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.RocketMqServiceTest;
import sc.hqu.graduationdesign.homeworkmanager.entity.TeacherEntity;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.FileUploadException;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericFileServiceProvider;

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

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/test/send")
    public String send(){
        serviceTest.publishMessage();
        return "success!";
    }

    @Autowired
    private GenericCacheProvider cacheProvider;

    @GetMapping(value = "/test/redis")
    public Object redisTest(){
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setJobTitle("讲师");
        return teacherEntity;
    }

    @PostMapping(value = "/test/json")
    public String testTransfer(@RequestBody TeacherEntity teacherEntity){
        System.out.println(teacherEntity);
        return "success";
    }

}
