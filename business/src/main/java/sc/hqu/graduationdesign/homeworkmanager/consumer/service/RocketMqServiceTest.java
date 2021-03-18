package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sc.hqu.graduationdesign.homeworkmanager.constant.NotificationType;
import sc.hqu.graduationdesign.homeworkmanager.model.*;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericMessagePublishProvider;
import sc.hqu.graduationdesign.homeworkmanager.util.NotificationMessageConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tzx
 * @date 2021-03-18 0:12
 */
@Component
public class RocketMqServiceTest {

    @Autowired
    private GenericMessagePublishProvider messagePublishProvider;

    public void publishMessage(){
        String phone1 = "S_18150115813";
        String name1 = "江文发";
        String phone2 = "18859132659";
        String name2 = "梁欣欣";
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
    }


    public static void main(String[] args) {
        int rows = 1024 * 1024 * 10;
        int columns = 6;
        long[][] twoDimensionArr = new long[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                twoDimensionArr[i][j] = 1;
            }
        }
        long sum1=0,sum2=0;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum1 += twoDimensionArr[i][j];
            }
        }
        long end1 = System.currentTimeMillis();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                sum2 += twoDimensionArr[j][i];
            }
        }
        long end2 = System.currentTimeMillis();
        System.out.println("横向再纵向求和耗费：" + (end1 - start1) + "ms");
        System.out.println("sum1 = " + sum1);
        System.out.println("纵向在横向求和耗费：" + (end2 - end1) + "ms");
        System.out.println("sum2 = " + sum2);
    }


}
