package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;

/**
 * 学生通信录视图模型类
 * @author tzx
 * @date 2021-03-14 2:27
 */
@Data
public class StudentContactListView {

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 学号
     */
    private Long studentNo;

    /**
     * 学生姓名
     */
    private String name;

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
