package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-06 22:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "短信服务数据输入对象")
public class SmsServiceInput extends BaseVo {

    private Long teacherNo;
    private String phone;
    private String type;
    private String code;

}
