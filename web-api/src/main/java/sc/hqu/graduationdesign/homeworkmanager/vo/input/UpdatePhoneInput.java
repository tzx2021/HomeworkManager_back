package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 22:36
 */
@Data
@ApiModel(value = "用户手机更新数据输入对象")
public class UpdatePhoneInput {

    private String phone;
    private String verifyCode;

}
