package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用的文本消息数据模型
 * @author tzx
 * @date 2021-03-17 17:19
 */
@Data
@NoArgsConstructor
public class CommonTextNotification {

    private NotificationPublish publish;
    private NotificationMessage message;

    public CommonTextNotification(NotificationPublish publish,NotificationMessage message){
        this.publish = publish;
        this.message = message;
    }

}
