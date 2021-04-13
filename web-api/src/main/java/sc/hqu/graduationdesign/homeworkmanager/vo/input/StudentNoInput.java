package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-13 21:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentNoInput extends BaseVo {

    private Long studentNo;
    private Long courseId;

}
