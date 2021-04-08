package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 学生班级关联表实体类
 * @author tzx
 * @date 2021-03-14 22:49
 */
@Data
public class StudentClassEntity {

    /**
     * 表id
     */
    private Long id;

    /**
     * 学号
     */
    private Long studentNo;

    /**
     * 班级id
     */
    private Long classId;

}
