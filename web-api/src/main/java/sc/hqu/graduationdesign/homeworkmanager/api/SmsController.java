package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.constant.SmsTemplate;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.UserService;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.MessageSendingException;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericShortMessageSendingProvider;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.SmsServiceInput;
import java.util.concurrent.TimeUnit;

/**
 * @author tzx
 * @date 2021-04-06 22:39
 */
@Api(tags = "手机短信服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/sms")
public class SmsController {


    @Autowired
    private GenericCacheProvider cacheProvider;

    @Autowired
    private GenericShortMessageSendingProvider messageSendingProvider;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "发送短信验证码")
    @PostMapping(value = "/send")
    public GenericResponse sendSms(@RequestBody SmsServiceInput input) throws MessageSendingException {
        String phone = input.getPhone();
        String verifyCode = RandomStringUtils.randomNumeric(6);
        String type = input.getType();
        switch (type){
            case "updatePass":
                // 需要校验当前的手机号是否是用户绑定的手机号
                if (userService.verifyPhone(input.getTeacherNo(),phone)){
                    messageSendingProvider.sendingMessage(
                            phone,
                            verifyCode,
                            SmsTemplate.UPDATE_PASS.getTemplateId()
                    );
                    String updatePassKey = type + "_" + phone;
                    // 缓存验证码
                    cacheProvider.set(updatePassKey,verifyCode,5 * 60L, TimeUnit.SECONDS);
                    break;
                }else {
                    return GenericResponse.error(ErrorCode.WRONG_BIND_PHONE);
                }
            case "updatePhone":
                messageSendingProvider.sendingMessage(
                        phone,
                        verifyCode,
                        SmsTemplate.UPDATE_PHONE.getTemplateId()
                );
                String updatePhoneKey = type + "_" + phone;
                // 缓存验证码
                cacheProvider.set(updatePhoneKey,verifyCode,5 * 60L, TimeUnit.SECONDS);
                break;
            default:break;
        }
        return GenericResponse.success();
    }

    @ApiOperation(value = "验证码验证")
    @PostMapping(value = "/verify")
    public GenericResponse verifySms(@RequestBody SmsServiceInput input){
        ErrorCode errorCode;
        String phone = input.getPhone();
        String type = input.getType();
        String code = input.getCode();
        String cacheKey = type + "_" + phone;
        String verifyCode = (String) cacheProvider.get(cacheKey);
        if (verifyCode != null){
            errorCode = verifyCode.equals(code) ? ErrorCode.SUCCESS : ErrorCode.WRONG_VERIFY_CODE;
        }else {
            errorCode = ErrorCode.VERIFY_CODE_EXPIRED;
        }
        String removeType = "updatePhone";
        if (errorCode.equals(ErrorCode.SUCCESS) && type.equals(removeType)){
            // 验证通过后需要移除验证码
            // 仅在此处执行移除updatePhone类型的验证码
            // updatePass类型的掩码会由密码修改时进行移除
            cacheProvider.remove(cacheKey);
        }
        // SUCCESS时，返回的是成功
        return GenericResponse.error(errorCode);
    }

}
