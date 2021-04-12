package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-06 22:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户密码更新数据输入对象")
public class UpdatePassInput extends BaseVo {

    private Long teacherNo;
    private String phone;
    private String password;
    private String verifyCode;

}
