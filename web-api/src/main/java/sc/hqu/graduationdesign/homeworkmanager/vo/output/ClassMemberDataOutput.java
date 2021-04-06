package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 1:22
 */
@Data
@ApiModel(value = "班级成员数据输出对象")
public class ClassMemberDataOutput {

    private Long studentNo;
    private String name;
    private Integer gender;
    private String contact;
    private String email;
    private String socialAccount;
    private String className;

}
