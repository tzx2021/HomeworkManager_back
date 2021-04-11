package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-06 23:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "班级创建数据输入对象")
public class CreateClassInput extends BaseVo {

    private Long id;
    private String name;
    private String classCode;
    private Integer totalStudentNum = 0;

}
