package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 21:06
 */
@Data
public class CourseUpdateDto {

    private Long id;
    private String name;
    private String classTime;
    private Integer coursePeriod;

}
