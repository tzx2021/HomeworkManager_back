package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;

/**
 * 学生家长通信录视图模型类
 * @author tzx
 * @date 2021-03-14 2:30
 */
@Data
public class ClassStudentParentView {

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 学号
     */
    private Long studentNo;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 家长id
     */
    private Long parentId;

    /**
     * 家长姓名
     */
    private String name;

    /**
     * 家长性别
     */
    private Integer gender;

    /**
     * 联系方式
     */
    private String contact;

}
