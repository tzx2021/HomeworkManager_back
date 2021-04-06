package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.SimpleFileOutput;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 2:18
 */
@Data
@ApiModel(value = "通知数据输出对象")
public class NotificationDataOutput {

    private Long id;
    private String title;
    private String content;
    private Integer type;
    private boolean confirmable;
    private Integer totalConfirm;
    private String createDate;
    private List<NotifyMemberInfo> notifyMembers;
    private List<SimpleFileOutput> attachments;

    @Data
    @ApiModel(value = "通知成员信息")
    public static class NotifyMemberInfo{
        private String memberType;
        private Long studentNo;
        private String name;
        private Integer gender;
        private String memberClass;
        private String memberContact;
    }

}
