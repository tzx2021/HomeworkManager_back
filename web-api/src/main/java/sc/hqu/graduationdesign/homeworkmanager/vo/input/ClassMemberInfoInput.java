package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 23:24
 */
@Data
@ApiModel(value = "班级成员信息数据对象")
public class ClassMemberInfoInput {

    private Long studentNo;
    private String name;
    private Integer gender;
    private String contact;
    private String email;
    private String socialAccount;
    private String fatherName;
    private String fatherContact;
    private String motherName;
    private String motherContact;

}
