package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 21:47
 */
@Data
public class MemberNotifyDto {

    private Long notificationId;
    private String memberType;
    private List<NotificationCreateDto.SimpleContactData> contactDataList;
}
