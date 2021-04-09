package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.ClassService;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
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

    @Autowired
    private ClassService classService;

    @ApiOperation(value = "获取班级成员分页数据")
    @PostMapping(value = "/page")
    public Object getMemberPage(@RequestBody PageQueryInput input){
        Long account = Long.getLong(SecurityContextUtil.userDetails().getUsername());
        return classService.getClassStudentPage(account, input.getPageSize(), input.getPageNum());
    }

    @ApiOperation(value = "班级成员删除")
    @PostMapping(value = "/delete")
    public GenericResponse deleteMember(@RequestBody DeleteMemberInput input){
        classService.removeClassStudent(input.getStudentNo());
        return GenericResponse.success();
    }

}
