package sc.hqu.graduationdesign.homeworkmanager.message;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;
import sc.hqu.graduationdesign.homeworkmanager.context.NotificationContext;

/**
 * @author tzx
 * @date 2021-04-11 14:48
 */
@Component
@RocketMQMessageListener(topic = "text-message",consumerGroup = "text-consumer-group")
public class TextNotificationHandler extends AbstractJsonMessageConsumer{

    @Override
    public void processingNotification(NotificationContext notificationContext) {
        System.out.println(notificationContext);
    }
}
