package sc.hqu.graduationdesign.homeworkmanager.entity;


import lombok.Data;

/**
 * 班级表实体类
 * @author tzx
 * @date 2021-03-14 1:54
 */
@Data
public class ClassEntity {

    /**
     * 班级id
     */
    private Integer id;

    /**
     * 班主任（教工号）
     */
    private Integer headTeacherNo;

    /**
     * 班级编号
     */
    private String classCode;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 学生总数
     */
    private Integer totalStudentNum;

}
