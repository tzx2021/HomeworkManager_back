package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 学生表实体类
 * @author tzx
 * @date 2021-03-14 1:58
 */
@Data
public class StudentEntity {

    /**
     * 学号
     */
    private Long studentNo;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别
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
