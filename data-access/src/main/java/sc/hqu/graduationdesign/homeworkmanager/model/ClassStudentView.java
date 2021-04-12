package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;

/**
 * 班级学生视图模型类
 * @author tzx
 * @date 2021-03-14 2:27
 */
@Data
public class ClassStudentView {

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学号
     */
    private Long studentNo;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生性别，0表示女生，1表示男生
     */
    private Integer gender;

    /**
     * 联系方式（手机号）
     */
    private String contact;

    /**
     * 学生邮箱
     */
    private String email;

    /**
     * 社交账号（qq号）
     */
    private String socialAccount;

}
