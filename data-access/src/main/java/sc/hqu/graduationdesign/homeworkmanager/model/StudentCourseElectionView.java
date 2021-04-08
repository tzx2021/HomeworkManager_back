package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-08 23:12
 */
@Data
public class StudentCourseElectionView {

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 学生学号
     */
    private Long studentNo;

    /**
     * 班级姓名
     */
    private String className;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生性别
     */
    private Integer gender;

    /**
     * 学生联系方式
     */
    private String contact;

}
