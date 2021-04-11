package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.*;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.CourseService;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
import sc.hqu.graduationdesign.homeworkmanager.utils.TimeFormatUtil;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.CreateCourseInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.DeleteCourseInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.UpdateCourseInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.CourseMetaDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.SimpleFileOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzx
 * @date 2021-04-06 23:40
 */
@Api(tags = "课程服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "获取课程信息数据集")
    @PostMapping(value = "/list")
    public List<CourseMetaDataOutput> getCourseMetaDataList(){
        Long teacherNo = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        List<CourseDataDto> courseDataByTeacherNo = courseService.getCourseDataByTeacherNo(teacherNo);
        // 数据封装和映射
        List<CourseMetaDataOutput> dataOutputs = new ArrayList<>(courseDataByTeacherNo.size());
        courseDataByTeacherNo.forEach(courseDataDto -> {
            CourseMetaDataOutput output = new CourseMetaDataOutput();
            BeanUtils.copyProperties(courseDataDto,output);
            output.setCreateDate(TimeFormatUtil.format(courseDataDto.getCreateDate()));
            // 封装选课数据
            List<CourseElectionData> courseElectionData = courseDataDto.getCourseElectionData();
            List<CourseMetaDataOutput.CourseElectionInfo> electionOutput = new ArrayList<>(courseElectionData.size());
            courseElectionData.forEach(courseElection -> {
                CourseMetaDataOutput.CourseElectionInfo electionInfo = new CourseMetaDataOutput.CourseElectionInfo();
                BeanUtils.copyProperties(courseElection,electionInfo);
                electionOutput.add(electionInfo);
            });

            // 封装文件数据
            List<SimpleFileDataDto> files = courseDataDto.getCourseFiles();
            List<SimpleFileOutput> courseFiles = new ArrayList<>(files.size());
            files.forEach(simpleFileDataDto -> {
                SimpleFileOutput fileOutput = new SimpleFileOutput();
                BeanUtils.copyProperties(simpleFileDataDto,fileOutput);
                courseFiles.add(fileOutput);
            });

            output.setCourseElectionData(electionOutput);
            output.setCourseFiles(courseFiles);
            dataOutputs.add(output);
        });
        return  dataOutputs;
    }

    @ApiOperation(value = "课程创建")
    @PostMapping(value = "/create")
    public GenericResponse createCourse(@RequestBody CreateCourseInput input){
        CourseCreateDto createDto = new CourseCreateDto();
        BeanUtils.copyProperties(input,createDto);
        CourseCreateDto dto = courseService.create(createDto);
        return GenericResponse.successWithData(dto);
    }

    @ApiOperation(value = "课程信息更新")
    @PostMapping(value = "/update")
    public GenericResponse updateCourse(@RequestBody UpdateCourseInput input){
        CourseUpdateDto updateDto = new CourseUpdateDto();
        BeanUtils.copyProperties(input,updateDto);
        courseService.update(updateDto);
        return GenericResponse.success();
    }

    @ApiOperation(value = "课程删除")
    @PostMapping(value = "/delete")
    public GenericResponse deleteCourse(@RequestBody DeleteCourseInput input){
        courseService.deleteCourse(input.getCourseId());
        return GenericResponse.success();
    }

}
