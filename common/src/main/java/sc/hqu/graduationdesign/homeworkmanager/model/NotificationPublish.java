package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author tzx
 * @date 2021-03-17 17:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPublish {

    private Long publisher;
    private List<Long> publishTo;
    private Boolean publishToClass;
    private Boolean publishToMembers;

    public Boolean isPublishToClass() {
        return publishToClass;
    }

    public Boolean isPublishToMembers() {
        return publishToMembers;
    }

}
