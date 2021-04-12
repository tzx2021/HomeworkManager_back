package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-06 22:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户手机更新数据输入对象")
public class UpdatePhoneInput extends BaseVo {

    private String phone;
    private String verifyCode;

}
