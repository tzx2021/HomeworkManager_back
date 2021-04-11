package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 20:32
 */
@Data
public class ClassDataDto {

    private Long id;
    private String name;
    private String classCode;
    private Integer totalStudentNum;
    private Long createDate;
    private List<ClassStudentDto> studentDtoList;
    private List<StudentParentDto> studentParentDtoList;
    private List<SimpleFileDataDto> fileDataDtoList;

}
