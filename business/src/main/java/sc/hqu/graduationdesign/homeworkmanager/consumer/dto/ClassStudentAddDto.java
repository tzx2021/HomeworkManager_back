package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 20:39
 */
@Data
public class ClassStudentAddDto {

    private Long classId;

    private List<ClassStudentDto> studentDtoList;

    private List<StudentParentDto> studentParentDtoList;

}
