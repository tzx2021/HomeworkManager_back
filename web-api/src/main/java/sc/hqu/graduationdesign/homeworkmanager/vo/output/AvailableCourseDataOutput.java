package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;


/**
 * @author tzx
 * @date 2021-04-13 21:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableCourseDataOutput extends BaseVo {

    private Long courseId;
    private String courseName;
    private String classTime;

}
