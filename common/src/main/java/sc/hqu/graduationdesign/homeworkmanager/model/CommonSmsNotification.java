package sc.hqu.graduationdesign.homeworkmanager.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author tzx
 * @date 2021-03-18 20:30
 */
@Data
@NoArgsConstructor
public class CommonSmsNotification {

    private NotificationAttachment contactMap;

    public CommonSmsNotification(Map<String,String> contactMap){
        NotificationAttachment attachment = new NotificationAttachment();
        attachment.setAttachmentData(contactMap);
        this.contactMap = attachment;
    }

    public String getTile(){
        return "推送短信提醒";
    }

}
