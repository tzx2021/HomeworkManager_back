package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 作息表实体类
 * @author tzx
 * @date 2021-03-14 2:21
 */
@Data
public class RestEntity {

    /**
     * 表id
     */
    private Integer id;

    /**
     * 时间段
     */
    private String period;

    /**
     * 作息描述
     */
    private String desc;

}
