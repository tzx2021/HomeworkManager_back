package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-06 23:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "班级成员导入数据输入对象")
public class AddClassMemberInput extends BaseVo {

    private Long classId;
    private List<ClassMemberInfoInput> memberList;


}
