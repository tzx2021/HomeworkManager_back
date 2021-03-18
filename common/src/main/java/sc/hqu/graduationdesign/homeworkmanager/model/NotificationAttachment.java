package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author tzx
 * @date 2021-03-17 17:36
 */
@Data
@NoArgsConstructor
public class NotificationAttachment {

    private Map<String,String> attachmentData;

    public NotificationAttachment(Map<String,String> attachmentData){
        this.attachmentData = attachmentData;
    }

}
