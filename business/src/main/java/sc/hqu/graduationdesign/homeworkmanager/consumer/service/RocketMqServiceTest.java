package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sc.hqu.graduationdesign.homeworkmanager.constant.NotificationType;
import sc.hqu.graduationdesign.homeworkmanager.model.*;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericMessagePublishProvider;
import sc.hqu.graduationdesign.homeworkmanager.util.NotificationMessageConverter;

import java.util.*;

/**
 * @author tzx
 * @date 2021-03-18 0:12
 */
@Component
public class RocketMqServiceTest {

    @Autowired
    private GenericMessagePublishProvider messagePublishProvider;

    public void publishMessage(){
        String phone1 = "S_15108596601";
        String name1 = "唐泽熙";
        String phone2 = "18716956363";
        String name2 = "陈城";
        Map<String,String> contact = new HashMap<>(8);
        contact.put(phone1, name1);
        contact.put(phone2, name2);
        NotificationPublish publish = new NotificationPublish();
        List<Long> publishTo = new ArrayList<>();
        publishTo.add(189123L);
        publishTo.add(8974231L);
        publishTo.add(8765412L);
        publish.setPublishTo(publishTo);
        publish.setPublisher(77812431L);
        publish.setPublishToClass(false);
        publish.setPublishToMembers(true);
        NotificationMessage messageBody = new NotificationMessage();
        messageBody.setPublishDate(System.currentTimeMillis());
        messageBody.setConfirmable(true);
        messageBody.setContent("哇哈哈哈哈或或");
        messageBody.setTitle("测试消息");
        messageBody.setType(NotificationType.ATTACHMENT.ordinal());
        CommonAttachmentNotification notificationHolder = new CommonAttachmentNotification(
                publish,messageBody,contact);
        messagePublishProvider.publishAsync(NotificationMessageConverter.convertToAttachmentMessage(notificationHolder));
        //CommonSmsNotification smsNotification = new CommonSmsNotification(contact);
        //messagePublishProvider.publishAsync(NotificationMessageConverter.convertToSmsMessage(smsNotification));
    }


}
