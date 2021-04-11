package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-10 1:26
 */
@Data
public class NotificationOutputDto {

    private Long id;
    private String title;
    private String content;
    private Integer type;
    private boolean confirmable;
    private int totalConfirm;
    private int confirmCount;
    private String memberType;
    private Long createDate;

}
