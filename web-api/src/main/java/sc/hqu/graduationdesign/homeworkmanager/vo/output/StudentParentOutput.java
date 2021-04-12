package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-12 9:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentParentOutput extends BaseVo {

    private Long parentId;
    private String studentName;
    private String parentName;
    private Integer gender;
    private String contact;

}
