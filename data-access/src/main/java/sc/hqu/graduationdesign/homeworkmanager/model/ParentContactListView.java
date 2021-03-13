package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;

/**
 * 学生家长通信录视图模型类
 * @author tzx
 * @date 2021-03-14 2:30
 */
@Data
public class ParentContactListView {

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 学号
     */
    private Long studentNo;

    /**
     * 家长id
     */
    private Integer parentId;

    /**
     * 家长姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String contact;

}
