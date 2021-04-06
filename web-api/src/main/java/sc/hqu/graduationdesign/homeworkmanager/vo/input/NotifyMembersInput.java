package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 2:33
 */
@Data
@ApiModel(value = "短信提醒成员数据输入对象")
public class NotifyMembersInput {

    private Long notificationId;
    private List<NotificationDataOutput.NotifyMemberInfo> memberInfoList;

}
