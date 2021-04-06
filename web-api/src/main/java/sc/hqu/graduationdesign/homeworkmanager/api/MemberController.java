package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.DeleteMemberInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.PageQueryInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.ClassMemberDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.GenericPageDataOutput;


/**
 * @author tzx
 * @date 2021-04-07 1:21
 */
@Api(tags = "班级成员服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/member")
public class MemberController {


    @ApiOperation(value = "获取班级成员分页数据")
    @PostMapping(value = "/page")
    public GenericPageDataOutput<ClassMemberDataOutput> getMemberPage(@RequestBody PageQueryInput input){
        return null;
    }

    @ApiOperation(value = "班级成员删除")
    @PostMapping(value = "/delete")
    public GenericResponse deleteMember(@RequestBody DeleteMemberInput input){
        return GenericResponse.success();
    }

}
