package sc.hqu.graduationdesign.homeworkmanager.entity;


import lombok.Data;

/**
 * 教师表实体类
 * @author tzx
 * @date 2021-03-14 1:49
 */
@Data
public class TeacherEntity {

    /**
     * 教工号
     */
    private Long teacherNo;

    /**
     * 教师姓名
     */
    private String name;

    /**
     * 教师性别
     */
    private Integer gender;

    /**
     * 联系方式（手机号码）
     */
    private String contact;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 职称
     */
    private String jobTitle;

}
