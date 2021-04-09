package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

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
    private Boolean confirmable;
    private Integer totalConfirm = 0;
    private String memberType;
    private List<SimpleContactData> contactDataList;
    private List<SimpleFileDataDto> attachments;

    @Data
    public static class SimpleContactData{
        private Long publishId;
        private String name;
        private String contact;
    }

}
