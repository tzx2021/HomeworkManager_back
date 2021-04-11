package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-06 23:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "课程原始数据输出对象")
public class CourseMetaDataOutput extends BaseVo {

    private Long id;
    private String name;
    private Float credit;
    private String classTime;
    private Integer coursePeriod;
    private String createDate;
    private List<CourseElectionInfo> courseElectionData;
    private List<SimpleFileOutput> courseFiles;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class CourseElectionInfo extends BaseVo{
        private Long studentNo;
        private String studentName;
        private String className;
        private Integer gender;
        private String contact;
    }

}
