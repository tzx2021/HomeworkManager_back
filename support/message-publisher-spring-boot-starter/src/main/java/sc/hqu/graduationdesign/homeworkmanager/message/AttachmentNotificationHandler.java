package sc.hqu.graduationdesign.homeworkmanager.message;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sc.hqu.graduationdesign.homeworkmanager.context.NotificationContext;


/**
 * 附件消息通知专用处理器类
 * @author tzx
 * @date 2021-03-18 21:00
 */
@Component
@RocketMQMessageListener(topic = "attachment-message",consumerGroup = "attach-consuming-group")
public class AttachmentNotificationHandler extends AbstractJsonMessageConsumer{

    private final Logger LOG = LoggerFactory.getLogger(AttachmentNotificationHandler.class);

    @Override
    public void processingNotification(NotificationContext notificationContext) {
        LOG.info("附件消息消费记录，消息上下文内容为: \n{}",notificationContext);
    }
}
