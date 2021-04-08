package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 20:37
 */
@Data
public class ClassCreateDto {

    private Long id;
    private String name;
    private String classCode;
    private Long headTeacherNo;
    private Long createDate;

}
