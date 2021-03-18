package sc.hqu.graduationdesign.homeworkmanager.context;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author tzx
 * @date 2021-03-18 22:30
 */
@Data
public class CommonNotificationContext implements NotificationContext{

    private String title;
    private String content;
    private Integer type;
    private Boolean confirmable;
    private Long publisher;
    private List<Long> publishTo;
    private Boolean publishToClass;
    private Boolean publishToMembers;
    private Long publishDate;
    private Map<String,String> attachment;

    @Override
    public Boolean isPublishToClass() {
        return publishToClass;
    }

    @Override
    public Boolean isPublishToMembers() {
        return publishToMembers;
    }

    @Override
    public Boolean isConfirmable() {
        return confirmable;
    }

}
