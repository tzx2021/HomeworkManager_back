package sc.hqu.graduationdesign.homeworkmanager.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tzx
 * @date 2021-04-09 11:10
 */
@AllArgsConstructor
@Getter
public enum MemberType {

    /**
     * 通知对象的类型
     */
    STUDENT("student"),
    PARENT("parent"),
    CLASS("class"),
    COURSE("course");

    private final String name;


}
