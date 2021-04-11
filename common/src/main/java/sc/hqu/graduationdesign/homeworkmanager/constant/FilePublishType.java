package sc.hqu.graduationdesign.homeworkmanager.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tzx
 * @date 2021-04-11 18:10
 */
@AllArgsConstructor
@Getter
public enum FilePublishType {

    /**
     * 文件发布对象类型
     */
    CLASS(1),
    COURSE(2),
    NOTIFICATION(3);

    private final int type;

}
