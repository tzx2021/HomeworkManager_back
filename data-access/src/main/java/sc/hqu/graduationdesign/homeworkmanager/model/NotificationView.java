package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;

/**
 * 通知视图模型类
 * @author tzx
 * @date 2021-03-14 2:25
 */
@Data
public class NotificationView {

    /**
     * 通知id
     */
    private Long id;

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
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件url
     */
    private String attachmentUrl;


}
