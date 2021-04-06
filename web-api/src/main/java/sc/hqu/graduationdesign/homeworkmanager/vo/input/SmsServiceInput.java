package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 22:41
 */
@Data
@ApiModel(value = "短信服务数据输入对象")
public class SmsServiceInput {

    private String phone;
    private String type;
    private String code;

}
