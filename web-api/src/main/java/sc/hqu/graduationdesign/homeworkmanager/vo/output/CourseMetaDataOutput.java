package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-06 23:33
 */
@Data
@ApiModel(value = "课程原始数据输出对象")
public class CourseMetaDataOutput {

    private Long id;
    private String name;
    private Float credit;
    private String classTime;
    private Integer coursePeriod;
    private String createDate;
    private List<CourseElectionInfo> courseElectionData;
    private List<SimpleFileOutput> courseFiles;


    public static class CourseElectionInfo{
        private Long studentNo;
        private String studentName;
        private String className;
        private Integer gender;
        private String contact;
    }

}
