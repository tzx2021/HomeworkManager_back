package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.CreateCourseInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.DeleteCourseInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.UpdateCourseInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.CourseMetaDataOutput;
import java.util.List;

/**
 * @author tzx
 * @date 2021-04-06 23:40
 */
@Api(tags = "课程服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/course")
public class CourseController {


    @ApiOperation(value = "获取课程信息数据集")
    @PostMapping(value = "/list")
    public List<CourseMetaDataOutput> getCourseMetaDataList(){
        return  null;
    }

    @ApiOperation(value = "课程创建")
    @PostMapping(value = "/create")
    public GenericResponse createCourse(@RequestBody CreateCourseInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "课程信息更新")
    @PostMapping(value = "/update")
    public GenericResponse updateCourse(@RequestBody UpdateCourseInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "课程删除")
    @PostMapping(value = "/delete")
    public GenericResponse deleteCourse(@RequestBody DeleteCourseInput input){
        return GenericResponse.success();
    }

}
