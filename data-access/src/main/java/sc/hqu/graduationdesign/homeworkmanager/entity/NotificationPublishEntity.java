package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-03-17 0:46
 */
@Data
public class NotificationPublishEntity {

    /**
     * 表id
     */
    private Long id;

    /**
     * 发布id，与学号、家长id以及班级id相关联
     * 通知允许向学生、家长或班级进行发布
     */
    private Long pid;

    /**
     * 通知id
     */
    private Long nid;



}
