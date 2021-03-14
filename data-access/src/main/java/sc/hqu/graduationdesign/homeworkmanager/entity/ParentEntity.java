package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 家长表实体类
 * @author tzx
 * @date 2021-03-14 2:13
 */
@Data
public class ParentEntity {

    /**
     * 家长id
     */
    private Integer id;

    /**
     * 学生学号
     */
    private Integer studentNo;

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
