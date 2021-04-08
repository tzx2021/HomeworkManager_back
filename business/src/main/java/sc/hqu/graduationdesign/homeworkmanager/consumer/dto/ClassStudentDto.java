package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 22:27
 */
@Data
public class ClassStudentDto {

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
