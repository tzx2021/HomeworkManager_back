package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 21:06
 */
@Data
public class CourseDataDto {

    private Long id;
    private String name;
    private Float credit;
    private String classTime;
    private Integer coursePeriod;
    private Long createDate;
    private List<CourseElectionData> courseElectionData;
    private List<SimpleFileDataDto> courseFiles;

}
