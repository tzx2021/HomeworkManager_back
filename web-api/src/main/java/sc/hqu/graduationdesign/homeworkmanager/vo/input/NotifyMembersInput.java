package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 2:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "短信提醒成员数据输入对象")
public class NotifyMembersInput extends BaseVo {

    private Long notificationId;
    private List<NotifyInfo> notifyInfos;
    private String memberType;

    @Data
    public static class NotifyInfo{
        private Long publishId;
        private String name;
        private String contact;
    }

}
