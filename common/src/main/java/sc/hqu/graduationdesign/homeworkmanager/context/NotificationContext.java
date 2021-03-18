package sc.hqu.graduationdesign.homeworkmanager.context;

import java.util.List;
import java.util.Map;

/**
 * @author tzx
 * @date 2021-03-18 22:23
 */
public interface NotificationContext {

    /**
     * 获取发布者ID
     * @return      发布者id
     */
    Long getPublisher();

    /**
     * 获取发布对象的id集合
     * @return      发布对象的id集合
     */
    List<Long> getPublishTo();

    /**
     * 是否是发布给班级的
     * @return      true代表班级是发布对象
     */
    Boolean isPublishToClass();

    /**
     * 是否是发布给成员的
     * @return      true代表班级成员或家长是发布对象
     */
    Boolean isPublishToMembers();

    /**
     * 获取通知标题
     * @return      通知标题
     */
    String getTitle();

    /**
     * 获取通知内容
     * @return      通知内容
     */
    String getContent();

    /**
     * 获取通知类型
     * @return      通知类型，0代表文本通知，1代表附件通知，2代表短信通知
     */
    Integer getType();

    /**
     * 是否需要确认通知
     * @return      true代表通知需要手动确认
     */
    Boolean isConfirmable();

    /**
     * 通知发布日期
     * @return      发布日期时间戳
     */
    Long getPublishDate();

    /**
     * 获取通知的附件信息
     * @return      附件信息的map
     */
    Map<String,String> getAttachment();

}
