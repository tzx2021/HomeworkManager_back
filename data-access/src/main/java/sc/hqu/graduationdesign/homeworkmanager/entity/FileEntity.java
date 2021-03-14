package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 文件表实体类
 * @author tzx
 * @date 2021-03-14 2:22
 */
@Data
public class FileEntity {

    /**
     * 文件id
     */
    private Integer id;

    /**
     * 系统账号
     */
    private Long account;

    /**
     * 发布id
     * 文件可以发布到班级和课程中，因此发布id可以课程id，也可以是班级id
     */
    private Integer publishId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 文件类型
     */
    private String type;

}
