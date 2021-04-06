package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.SmsServiceInput;

/**
 * @author tzx
 * @date 2021-04-06 22:39
 */
@Api(tags = "手机短信服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/sms")
public class SmsController {

    public GenericResponse sendSms(@RequestBody SmsServiceInput input){
        return GenericResponse.success();
    }

    public GenericResponse verifySms(@RequestBody SmsServiceInput input){
        return GenericResponse.success();
    }

}
