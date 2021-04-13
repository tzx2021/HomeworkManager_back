package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import lombok.Data;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-13 22:10
 */
@Data
public class CourseSelectingInput {


    private List<CourseSelect> courseSelectList;

    @Data
    public static class CourseSelect{
        private Long courseId;
        private Long studentNo;
    }

}
