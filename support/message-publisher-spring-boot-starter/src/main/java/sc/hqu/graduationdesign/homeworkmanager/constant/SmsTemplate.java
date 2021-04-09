package sc.hqu.graduationdesign.homeworkmanager.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tzx
 * @date 2021-04-09 9:51
 */
@Getter
@AllArgsConstructor
public enum SmsTemplate {

    /**
     * 短信提醒模板
     */
    SMS_REMIND("918758"),
    STUDENT_REMIND("895874"),
    PARENT_REMIND("895876"),
    UPDATE_PASS("623429"),
    UPDATE_PHONE("623326");

    private final String templateId;

}
