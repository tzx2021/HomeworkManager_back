package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 通知表实体类
 * @author tzx
 * @date 2021-03-14 2:16
 */
@Data
public class NotificationEntity {

    /**
     * 通知id
     */
    private Long id;

    /**
     * 系统账号
     */
    private Long account;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知类型，0表示普通的文本通知，1表示附件通知
     */
    private Integer type;

    /**
     * 通知成员数量
     */
    private Integer notifyMembers;

    /**
     * 已读数量，与通知成员数量相对应
     */
    private Integer totalRead;

    /**
     * 附件url，多个附件使用逗号','隔开
     */
    private String attachmentUrl;

}
