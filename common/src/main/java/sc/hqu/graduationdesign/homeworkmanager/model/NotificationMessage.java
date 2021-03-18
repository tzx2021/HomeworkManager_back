package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author tzx
 * @date 2021-03-17 13:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {

    private String title;
    private Integer type;
    private String content;
    private Boolean confirmable;
    private Long publishDate;


    public Boolean isConfirmable() {
        return confirmable;
    }


}
