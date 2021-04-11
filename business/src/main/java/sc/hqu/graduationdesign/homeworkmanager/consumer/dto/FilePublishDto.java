package sc.hqu.graduationdesign.homeworkmanager.consumer.dto;


import lombok.Data;


/**
 * @author tzx
 * @date 2021-04-11 16:19
 */
@Data
public class FilePublishDto {

    private Long pid;
    private String name;
    private String url;

    /**
     * 发布对象的类型,1 - 班级。2 - 课程，3 - 通知
     */
    private Integer type;

}
