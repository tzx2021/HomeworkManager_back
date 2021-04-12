package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-12 9:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleClassOutput extends BaseVo {

    private Long classId;
    private String className;
    private String classCode;
    private Integer totalStudentNum;

}
