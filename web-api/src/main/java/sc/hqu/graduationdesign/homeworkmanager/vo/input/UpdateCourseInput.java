package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 23:44
 */
@Data
@ApiModel(value = "课程信息更新数据输入对象")
public class UpdateCourseInput {

    private Long id;
    private String name;
    private String classTime;
    private Integer coursePeriod;

}
