package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 课程实体类
 * @author tzx
 * @date 2021-03-14 2:07
 */
@Data
public class CourseEntity {

    /**
     * 课程id
     */
    private Integer id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 学分
     */
    private float credit;

    /**
     * 上课时间
     */
    private String classTime;

    /**
     * 课程周期，单位为周
     */
    private Integer coursePeriod;

}
