package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 1:07
 */
@Data
@ApiModel(value = "通知发布数据输入对象")
public class CreateNotificationInput {

    private String title;
    private String content;
    private Integer type;
    private boolean confirmable;
    private List<Long> pidList;
    private String memberType;
    private List<Long> attachments;

}
