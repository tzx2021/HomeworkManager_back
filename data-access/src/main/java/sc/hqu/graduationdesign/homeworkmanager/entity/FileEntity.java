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
