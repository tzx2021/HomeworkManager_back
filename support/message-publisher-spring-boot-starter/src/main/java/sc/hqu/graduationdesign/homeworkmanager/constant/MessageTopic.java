package sc.hqu.graduationdesign.homeworkmanager.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tzx
 * @date 2021-03-17 14:19
 */
@AllArgsConstructor
@Getter
public enum MessageTopic {

    /**
     * 消息主题，目前支持3种
     */
    TEXT_MESSAGE("text-message"),
    ATTACHMENT_MESSAGE("attachment-message"),
    SMS_MESSAGE("sms-message"),

    /**对于undefined类型消费者可以不做任何处理，但是生产者依旧会投放该主题的消息*/
    UNDEFINED("undefined");

    private final String topic;

}
