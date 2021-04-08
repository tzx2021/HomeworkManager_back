package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 22:29
 */
@Data
public class StudentParentDto {

    private Long studentNo;
    private String fatherName;
    private String fatherContact;
    private String motherName;
    private String motherContact;

}
