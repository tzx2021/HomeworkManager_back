package sc.hqu.graduationdesign.homeworkmanager.vo.output;


import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-06 22:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "班级原始数据输出对象")
public class ClassMetaDataOutput extends BaseVo {

    private Long id;
    private String name;
    private String classCode;
    private Integer totalStudentNum;
    private String createDate;
    private List<ClassMemberDataOutput> memberData;
    private List<SimpleFileOutput> classFiles;
    private List<MemberParentInfo> memberParentData;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @ApiModel(value = "成员家长信息数据对象")
    public static class MemberParentInfo extends BaseVo{
        private Long id;
        private String studentName;
        private String name;
        private String relation;
        private String contact;
    }

}
