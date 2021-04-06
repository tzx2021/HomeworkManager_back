package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.AddClassMemberInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.CreateClassInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.DeleteClassInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.UpdateClassInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.ClassMetaDataOutput;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-06 22:52
 */
@Api(tags = "班级服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/class")
public class ClassController {


    @ApiOperation(value = "获取班级信息数据集")
    @PostMapping(value = "/list")
    public List<ClassMetaDataOutput> getClassDataList(){
        return null;
    }


    @ApiOperation(value = "班级创建")
    @PostMapping(value = "/create")
    public GenericResponse createClass(@RequestBody CreateClassInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "班级信息更新")
    @PostMapping(value = "/update")
    public GenericResponse updateClass(@RequestBody UpdateClassInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "导入班级成员")
    @PostMapping(value = "/member/add")
    public GenericResponse addMember(@RequestBody AddClassMemberInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "班级删除")
    @PostMapping(value = "/delete")
    public GenericResponse deleteClass(@RequestBody DeleteClassInput input){
        return GenericResponse.success();
    }

}
