package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 22:34
 */
@Data
@ApiModel(value = "用户密码更新数据输入对象")
public class UpdatePassInput {

    private String phone;
    private String password;
    private String verifyCode;

}
