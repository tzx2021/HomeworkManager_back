package sc.hqu.graduationdesign.homeworkmanager.message;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sc.hqu.graduationdesign.homeworkmanager.context.NotificationContext;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.MessageSendingException;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericShortMessageSendingProvider;

import java.util.*;

/**
 * 短信消息通知专用处理器类
 * @author tzx
 * @date 2021-03-17 18:44
 */
@RocketMQMessageListener(topic = "sms-message",consumerGroup = "${rocketmq.consumer.group}")
public class SmsNotificationHandler extends AbstractJsonMessageConsumer {

    private final GenericShortMessageSendingProvider messageSendingProvider;

    private final Logger LOG = LoggerFactory.getLogger(SmsNotificationHandler.class);

    public SmsNotificationHandler(GenericShortMessageSendingProvider messageSendingProvider){
        this.messageSendingProvider = messageSendingProvider;
    }

    @Override
    public void processingNotification(NotificationContext notificationContext) {
        Map<String, String> contactMap = notificationContext.getAttachment();
        String studentPrefix = "S";
        // 号码信息封装时需要约定，学生的号码会加上S作为前缀，例如：S_13131313131
        contactMap.forEach((phoneNum,name) -> {
            if (phoneNum.startsWith(studentPrefix)) {
                String realPhoneNum = phoneNum.split("_")[1];
                doMessageSending(realPhoneNum, name, 0);
            } else {
                doMessageSending(phoneNum, name, 1);
            }
        });
    }

    /**
     * 短信发送处理
     * @param phoneNum      手机号码
     * @param param         短信参数
     * @param template      短信模板索引
     */
    private void doMessageSending(String phoneNum,String param,int template) {
        try {
            messageSendingProvider.sendingMessage(phoneNum, param, template);
            LOG.info("Sending reminding message to phone num [{}] successfully!",phoneNum);
        } catch (MessageSendingException e) {
            LOG.error(e.getMessage(),e);
        }
    }

}
