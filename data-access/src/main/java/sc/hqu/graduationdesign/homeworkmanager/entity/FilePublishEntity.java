package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 文件发布关联表实体类
 * @author tzx
 * @date 2021-03-17 0:42
 */
@Data
public class FilePublishEntity {

    /**
     * 表id
     */
    private Long id;

    /**
     * 文件id
     */
    private Long fid;

    /**
     * 发布id，与班级id或课程id或通知id进行关联
     * 文件可以发布到课程或班级或通知中
     */
    private Long pid;

    /**
     * 发布对象的类型，1代表班级，2代表课程，3代表通知
     */
    private Integer publishType;

}
