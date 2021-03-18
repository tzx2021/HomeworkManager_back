package sc.hqu.graduationdesign.homeworkmanager.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 通用的附件消息数据模型
 * @author tzx
 * @date 2021-03-17 17:45
 */
@Data
@NoArgsConstructor
public class CommonAttachmentNotification{

    private NotificationPublish publish;
    private NotificationMessage message;
    private NotificationAttachment attachment;

    public CommonAttachmentNotification(NotificationPublish publish, NotificationMessage message, Map<String,String> attachmentData){
        this.publish = publish;
        this.message = message;
        NotificationAttachment attachment = new NotificationAttachment();
        attachment.setAttachmentData(attachmentData);
        this.attachment = attachment;
    }

}
