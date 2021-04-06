package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 23:31
 */
@Data
@ApiModel(value = "班级删除数据输入对象")
public class DeleteCourseInput {

    private Long courseId;

}
