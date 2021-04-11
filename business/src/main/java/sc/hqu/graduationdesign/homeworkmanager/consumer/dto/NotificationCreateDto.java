package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 21:45
 */
@Data
public class NotificationCreateDto {

    private Long id;
    private String title;
    private String content;
    private Integer type;
    private Integer confirmable;
    private Integer totalConfirm = 0;
    private String memberType;
    private List<SimpleContactData> contactDataList;
    private List<FilePublishDto> attachments;

    @Data
    public static class SimpleContactData{
        private Long publishId;
        private String name;
        private String contact;
    }

}
