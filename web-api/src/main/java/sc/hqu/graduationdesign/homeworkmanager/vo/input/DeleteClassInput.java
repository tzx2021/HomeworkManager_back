package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-06 23:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "班级删除数据输入对象")
public class DeleteClassInput extends BaseVo {

    private Long classId;

}
