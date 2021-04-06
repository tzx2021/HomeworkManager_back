package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
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



    public GenericResponse updatePass(@RequestBody UpdatePassInput input){
        return GenericResponse.success();
    }


    public GenericResponse updatePhone(@RequestBody UpdatePhoneInput input){
        return GenericResponse.success();
    }

}
