package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-06 23:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "班级创建数据输入对象")
public class CreateCourseInput extends BaseVo {

    private String name;
    private Float credit;
    private String classTime;
    private Integer coursePeriod;

}
