package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;

/**
 * @author tzx
 * @date 2021-04-06 22:26
 */
@Api(tags = "认证服务")
@RestController
@RequestMapping("/api/homeworkmanager")
public class AuthenticationController {


    @ApiOperation(value = "登录认证")
    @PostMapping(value = "/login")
    public GenericResponse login(
            @ApiParam(value = "登录用户名", required = true) String username,
            @ApiParam(value = "登录密码", required = true) String password
    ){
        return null;
    }

    @ApiOperation(value = "用户登出")
    @PostMapping(value = "/logout")
    public void logout(){

    }

}
