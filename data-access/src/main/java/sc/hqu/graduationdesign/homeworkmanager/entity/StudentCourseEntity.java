package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 学生课程关联表实体类
 * @author tzx
 * @date 2021-03-14 22:43
 */
@Data
public class StudentCourseEntity {

    /**
     * 表id
     */
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 学生学号
     */
    private Long studentNo;

}
