package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.*;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.ClassService;
import sc.hqu.graduationdesign.homeworkmanager.exception.BusinessException;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
import sc.hqu.graduationdesign.homeworkmanager.utils.TimeFormatUtil;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.*;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.ClassMemberDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.ClassMetaDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.SimpleClassOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.SimpleFileOutput;

import java.util.*;

/**
 * @author tzx
 * @date 2021-04-06 22:52
 */
@Api(tags = "班级服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @ApiOperation(value = "获取班级信息数据集")
    @PostMapping(value = "/list")
    public List<ClassMetaDataOutput> getClassDataList(){
        String username = SecurityContextUtil.userDetails().getUsername();
        List<ClassDataDto> classDataByTeacherNo = classService.getClassDataByTeacherNo(Long.valueOf(username));
        List<ClassMetaDataOutput> dataOutputs = new ArrayList<>(classDataByTeacherNo.size());
        // 数据类型转换和映射
        classDataByTeacherNo.forEach(classDataDto -> {
            ClassMetaDataOutput output = new ClassMetaDataOutput();
            BeanUtils.copyProperties(classDataDto,output);
            // 转换时间
            output.setCreateDate(TimeFormatUtil.format(classDataDto.getCreateDate()));
            // 映射学生数据
            List<ClassStudentDto> studentDtoList = classDataDto.getStudentDtoList();
            List<ClassMemberDataOutput> memberData = new ArrayList<>(studentDtoList.size());
            studentDtoList.forEach(classStudentDto -> {
                ClassMemberDataOutput memberDataOutput = new ClassMemberDataOutput();
                BeanUtils.copyProperties(classStudentDto,memberDataOutput);
                memberData.add(memberDataOutput);
            });

            // 映射学生家长数据
            List<StudentParentDto> studentParentDtoList = classDataDto.getStudentParentDtoList();
            List<ClassMetaDataOutput.MemberParentInfo> memberParentData = new ArrayList<>(studentParentDtoList.size());
            studentParentDtoList.forEach(studentParentDto -> {
                String fatherName = studentParentDto.getFatherName();
                String motherName = studentParentDto.getMotherName();
                Long studentNo = studentParentDto.getStudentNo();
                String studentName = Objects.requireNonNull(studentDtoList.stream().
                        filter(classStudentDto -> classStudentDto.getStudentNo().equals(studentNo))
                        .findFirst().orElse(null)).getName();
                ClassMetaDataOutput.MemberParentInfo memberParentInfo = new ClassMetaDataOutput.MemberParentInfo();
                memberParentInfo.setStudentName(studentName);
                memberParentInfo.setId(studentParentDto.getStudentNo());
                if (StringUtils.hasLength(fatherName)){
                    memberParentInfo.setContact(studentParentDto.getFatherContact());
                    memberParentInfo.setName(fatherName);
                    memberParentInfo.setRelation("父亲");
                }else {
                    memberParentInfo.setContact(studentParentDto.getMotherContact());
                    memberParentInfo.setName(motherName);
                    memberParentInfo.setRelation("母亲");
                }
                memberParentData.add(memberParentInfo);
            });

            // 映射文件数据
            List<SimpleFileDataDto> fileDataDtoList = classDataDto.getFileDataDtoList();
            List<SimpleFileOutput> classFiles = new ArrayList<>(fileDataDtoList.size());
            fileDataDtoList.forEach(simpleFileDataDto -> {
                SimpleFileOutput fileOutput = new SimpleFileOutput();
                BeanUtils.copyProperties(simpleFileDataDto,fileOutput);
                classFiles.add(fileOutput);
            });

            output.setMemberParentData(memberParentData);
            output.setMemberData(memberData);
            output.setClassFiles(classFiles);
            dataOutputs.add(output);
        });
        return dataOutputs;
    }

    @ApiOperation(value = "获取所有班级的简易信息")
    @PostMapping(value = "/all")
    public List<SimpleClassOutput> getAllClasses(){
        Long teacherNo = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        List<ClassDataDto> simpleClassByTeacherNo = classService.getSimpleClassByTeacherNo(teacherNo);
        List<SimpleClassOutput> outputs = new ArrayList<>(simpleClassByTeacherNo.size());
        simpleClassByTeacherNo.forEach(classDataDto -> {
            SimpleClassOutput output = new SimpleClassOutput();
            output.setClassId(classDataDto.getId());
            output.setClassName(classDataDto.getName());
            output.setClassCode(classDataDto.getClassCode());
            output.setTotalStudentNum(classDataDto.getTotalStudentNum());
            outputs.add(output);
        });
        return outputs;
    }


    @ApiOperation(value = "班级创建")
    @PostMapping(value = "/create")
    public GenericResponse createClass(@RequestBody CreateClassInput input){
        ClassCreateDto classCreateDto = new ClassCreateDto();
        BeanUtils.copyProperties(input,classCreateDto);
        classCreateDto.setHeadTeacherNo(Long.valueOf(SecurityContextUtil.userDetails().getUsername()));
        classService.create(classCreateDto);
        return GenericResponse.successWithData(classCreateDto);
    }

    @ApiOperation(value = "班级信息更新")
    @PostMapping(value = "/update")
    public GenericResponse updateClass(@RequestBody UpdateClassInput input){
        ClassUpdateDto updateDto = new ClassUpdateDto();
        BeanUtils.copyProperties(input,updateDto);
        classService.update(updateDto);
        return GenericResponse.success();
    }

    @ApiOperation(value = "导入班级成员")
    @PostMapping(value = "/member/add")
    public GenericResponse addMember(@RequestBody AddClassMemberInput input){
        System.out.println(input);
        ClassStudentAddDto addDto = new ClassStudentAddDto();
        addDto.setClassId(input.getClassId());
        List<ClassMemberInfoInput> memberList = input.getMemberList();
        List<ClassStudentDto> studentDtoList = new ArrayList<>(memberList.size());
        List<StudentParentDto> studentParentDtoList = new ArrayList<>(memberList.size());
        // 数据映射
        memberList.forEach(classMemberInfoInput -> {
            ClassStudentDto studentDto = new ClassStudentDto();
            BeanUtils.copyProperties(classMemberInfoInput,studentDto);
            studentDtoList.add(studentDto);
            StudentParentDto parentDto = new StudentParentDto();
            BeanUtils.copyProperties(classMemberInfoInput,parentDto);
            studentParentDtoList.add(parentDto);
            addDto.setStudentDtoList(studentDtoList);
            addDto.setStudentParentDtoList(studentParentDtoList);
        });
        classService.batchAddStudent(addDto);
        return GenericResponse.success();
    }

    @ApiOperation(value = "班级删除")
    @PostMapping(value = "/delete")
    public GenericResponse deleteClass(@RequestBody DeleteClassInput input) throws BusinessException {
        classService.delete(input.getClassId());
        return GenericResponse.success();
    }

}
