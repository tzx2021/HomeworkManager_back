package sc.hqu.graduationdesign.homeworkmanager.util;

import com.alibaba.fastjson.JSON;
import sc.hqu.graduationdesign.homeworkmanager.constant.MessageTopic;
import sc.hqu.graduationdesign.homeworkmanager.model.*;


/**
 * @author tzx
 * @date 2021-03-17 13:39
 */
public class NotificationMessageConverter {

    public static GenericMessageModel convertToTextMessage(CommonTextNotification textNotification) {
        RocketMqMessage rocketMqMessage = new RocketMqMessage();
        rocketMqMessage.setTopic(MessageTopic.TEXT_MESSAGE.getTopic());
        rocketMqMessage.setTag(textNotification.getMessage().getTitle());
        rocketMqMessage.setJsonData(JSON.toJSONString(textNotification));
        return rocketMqMessage;
    }

    public static GenericMessageModel convertToAttachmentMessage(CommonAttachmentNotification attachmentNotification) {
        RocketMqMessage rocketMqMessage = new RocketMqMessage();
        rocketMqMessage.setTopic(MessageTopic.ATTACHMENT_MESSAGE.getTopic());
        return processingMessage(rocketMqMessage, attachmentNotification.getMessage().getTitle(), attachmentNotification);
    }

    public static GenericMessageModel convertToSmsMessage(CommonSmsNotification smsNotification){
        RocketMqMessage rocketMqMessage = new RocketMqMessage();
        rocketMqMessage.setTopic(MessageTopic.SMS_MESSAGE.getTopic());
        // 对于sms主题的消息只需要将联系人数据发送出去即可
        // 目前sms主题的消息只在系统内部进行消费并处理
        return processingMessage(rocketMqMessage, smsNotification.getTile(), smsNotification);
    }

    private static GenericMessageModel processingMessage(RocketMqMessage rocketMqMessage,String tag,Object data){
        rocketMqMessage.setTag(tag);
        rocketMqMessage.setJsonData(JSON.toJSONString(data));
        return rocketMqMessage;
    }

}
