package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * TODO     首先文件可以单独上传，上传后的文件可以单独发布到班级或者课程里
 *          上传后的文件也可以在通知发布时被选择为通知的附件
 *          但是，发布后的文件，如果个人在文件管理界面进行删除了
 *          应该出现的结果是：
 *          个人文件管理界面看不到该文件了，但是在发布该文件的界面中，
 *          该文件还是可见并且可下载的
 *          因此对文件实体应该增加deleteAt标识来标记个人已删除记录
 *          但是个人进行删除时，不删除file的关联表中的记录，此时仍可以通过关联表查询到该记录
 *          而个人直接查询时需要查询deleteAt=0的记录
 *
 * 文件表实体类
 * @author tzx
 * @date 2021-03-14 2:22
 */
@Data
public class FileEntity {

    /**
     * 文件id
     */
    private Long id;

    /**
     * 系统账号
     */
    private Long account;

    /**
     * 发布状态，默认是0,0代表未进行过任何发布,1代表已经过任意发布
     */
    private Integer publishState;

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

    /**
     * 上传日期时间戳
     */
    private Long uploadDate;

    /**
     * 删除时间戳
     * 0是默认值，表示未删除
     */
    private Long deleteAt;

}
