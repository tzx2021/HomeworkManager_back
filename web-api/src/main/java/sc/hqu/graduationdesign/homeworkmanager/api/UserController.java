package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.UserService;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
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

    @ApiOperation(value = "更新登录密码")
    @PostMapping(value = "/update/pass")
    public GenericResponse updatePass(@RequestBody UpdatePassInput input){
        Long account = Long.getLong(SecurityContextUtil.userDetails().getUsername());
        userService.updatePass(account,input.getPassword());
        return GenericResponse.success();
    }

    @ApiOperation(value = "更新绑定手机")
    @PostMapping(value = "/update/phone")
    public GenericResponse updatePhone(@RequestBody UpdatePhoneInput input){
        Long account = Long.getLong(SecurityContextUtil.userDetails().getUsername());
        userService.updatePhoneNum(account,input.getPhone());
        return GenericResponse.success();
    }

}
