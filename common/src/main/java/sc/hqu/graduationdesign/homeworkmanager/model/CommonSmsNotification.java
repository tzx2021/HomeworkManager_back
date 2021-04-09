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

    /**
     * 短信推送有两种形式，一种是单纯的用户提醒，即该content属性为null时会推送这类短信
     * 当content的内容不为空时，那么会推送具体内容的短信
     */
    private String content = null;

    public CommonSmsNotification(Map<String,String> contactMap){
        NotificationAttachment attachment = new NotificationAttachment();
        attachment.setAttachmentData(contactMap);
        // 该map的key是联系方式，val是名字
        this.contactMap = attachment;
    }

    public String getTile(){
        return "推送短信提醒";
    }

}
