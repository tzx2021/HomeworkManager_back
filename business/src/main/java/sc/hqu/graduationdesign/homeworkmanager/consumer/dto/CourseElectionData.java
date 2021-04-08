package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-08 22:33
 */
@Data
public class CourseElectionData {

    private Long studentNo;
    private String studentName;
    private String className;
    private Integer gender;
    private String contact;

}
