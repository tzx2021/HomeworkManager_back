package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.UserService;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.UpdatePassInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.UpdatePhoneInput;

/**
 * @author tzx
 * @date 2021-04-06 22:33
 */
@Api(tags = "用户服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private GenericCacheProvider cacheProvider;

    @ApiOperation(value = "更新登录密码")
    @PostMapping(value = "/update/pass")
    public GenericResponse updatePass(@RequestBody UpdatePassInput input){
        String key = "updatePass_" + input.getPhone();
        String code = (String) cacheProvider.get(key);
        if (code != null && code.equals(input.getVerifyCode())){
            userService.updatePass(input.getTeacherNo(),input.getPassword());
            // 密码修改成功后移除key
            cacheProvider.remove(key);
            return GenericResponse.success();
        }else {
            return GenericResponse.error(ErrorCode.INVALID_VERIFY_CODE);
        }
    }

    @ApiOperation(value = "更新绑定手机")
    @PostMapping(value = "/update/phone")
    public GenericResponse updatePhone(@RequestBody UpdatePhoneInput input){
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        userService.updatePhoneNum(account,input.getPhone());
        return GenericResponse.success();
    }

}
