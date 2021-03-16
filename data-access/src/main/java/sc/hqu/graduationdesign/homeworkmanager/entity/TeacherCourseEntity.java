package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 教师课程关联表实体类
 * @author tzx
 * @date 2021-03-14 22:46
 */
@Data
public class TeacherCourseEntity {

    /**
     * 表id
     */
    private Long id;

    /**
     * 教工号
     */
    private Long teacherNo;

    /**
     * 课程id
     */
    private Long courseId;

}
