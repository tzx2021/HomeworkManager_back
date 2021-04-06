package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 23:06
 */
@Data
@ApiModel(value = "班级创建数据输入对象")
public class CreateClassInput {

    private String name;
    private String classCode;

}
