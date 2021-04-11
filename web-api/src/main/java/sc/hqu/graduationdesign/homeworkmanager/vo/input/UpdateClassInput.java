package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-06 23:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "班级信息更新数据输入对象")
public class UpdateClassInput extends BaseVo {

    private Long id;
    private String name;
    private String classCode;

}
