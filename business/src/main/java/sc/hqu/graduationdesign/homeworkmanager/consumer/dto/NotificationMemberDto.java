package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-09 10:54
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class NotificationMemberDto{

    private String memberType;

    private Long studentNo;

    private Long parentId;

    private String name;

    private Integer gender;

    private String className;

    private String contact;

}
