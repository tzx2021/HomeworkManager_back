package sc.hqu.graduationdesign.homeworkmanager.api;

import com.alibaba.fastjson.PropertyNamingStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.ClassService;
import sc.hqu.graduationdesign.homeworkmanager.model.ClassStudentParentView;
import sc.hqu.graduationdesign.homeworkmanager.model.ClassStudentView;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.DeleteMemberInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.PageQueryInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.ClassMemberDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.GenericPageDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.StudentParentOutput;

import java.util.ArrayList;
import java.util.List;


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
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        return classService.getClassStudentPage(account, input.getPageSize(), input.getPageNum());
    }

    @ApiOperation(value = "获取教师的所有学生")
    @PostMapping(value = "/all")
    public List<ClassMemberDataOutput> getAllStudents(){
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        List<ClassStudentView> allStudentByTeacherNo = classService.getAllStudentByTeacherNo(account);
        List<ClassMemberDataOutput> outputs = new ArrayList<>(allStudentByTeacherNo.size());
        allStudentByTeacherNo.forEach(classStudentView -> {
            ClassMemberDataOutput output = new ClassMemberDataOutput();
            BeanUtils.copyProperties(classStudentView,output);
            outputs.add(output);
        });
        return outputs;
    }

    @ApiOperation(value = "获取教师所有学生的家长")
    @PostMapping(value = "/parents")
    public List<StudentParentOutput> getAllStudentParents(){
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        List<ClassStudentParentView> allParentByTeacherNo = classService.getAllParentByTeacherNo(account);
        List<StudentParentOutput> outputs = new ArrayList<>(allParentByTeacherNo.size());
        allParentByTeacherNo.forEach(classStudentParentView -> {
            StudentParentOutput output = new StudentParentOutput();
            BeanUtils.copyProperties(classStudentParentView,output);
            output.setParentName(classStudentParentView.getName());
            outputs.add(output);
        });
        return outputs;
    }

    @ApiOperation(value = "班级成员删除")
    @PostMapping(value = "/delete")
    public GenericResponse deleteMember(@RequestBody DeleteMemberInput input){
        System.out.println(input);
        classService.removeClassStudent(input.getStudentNo());
        return GenericResponse.success();
    }

}
