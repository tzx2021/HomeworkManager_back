package sc.hqu.graduationdesign.homeworkmanager.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.rocketmq.spring.core.RocketMQListener;
import sc.hqu.graduationdesign.homeworkmanager.constant.NotificationType;
import sc.hqu.graduationdesign.homeworkmanager.context.CommonNotificationContext;
import sc.hqu.graduationdesign.homeworkmanager.context.NotificationContext;
import sc.hqu.graduationdesign.homeworkmanager.model.NotificationMessage;
import sc.hqu.graduationdesign.homeworkmanager.model.NotificationPublish;

import java.util.Map;

/**
 * 消息队列的json消息消费者基类，在该抽象类中将信息数据封装的方法抽象出来
 * 子类只需要获取具体的上下文对象，通过上下文对象获取对应需要处理的数据去完成处理逻辑即可
 * 这种模板设计好处是可以在上层抽取并封装实现了公共方法，并且由于子类是具体的消息消费者
 * 因此最终某个主题的消息一定是由对应的子类去消费的，父类不需要关系，只需要将处理方法进行调用即可
 *
 * @author tzx
 * @date 2021-03-18 22:37
 */
public abstract class AbstractJsonMessageConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 用于封装通知的上下文数据
        CommonNotificationContext notificationContext = new CommonNotificationContext();
        JSONObject jsonObject = JSON.parseObject(message);
        // 尝试获取contactMap，如果非空，那么意味着是sms主题的消息
        JSONObject contactMap = jsonObject.getJSONObject("contactMap");
        if (contactMap != null){
            // 处理sms主题的消息数据，只需要将里面的map数据取出
            Map<String,String> contactAttachment = JSONObject.parseObject(contactMap.getJSONObject("attachmentData").toJSONString(), new TypeReference<Map<String,String>>(){});
            notificationContext.setAttachment(contactAttachment);
        }else {
            JSONObject attachment = jsonObject.getJSONObject("attachment");
            // 文本消息和附件消息都带有message和publish属性
            // 因此这里需要将公共数据取出并封装
            JSONObject msgBody = jsonObject.getJSONObject("message");
            JSONObject publish = jsonObject.getJSONObject("publish");
            NotificationMessage nm = JSON.parseObject(msgBody.toJSONString(), NotificationMessage.class);
            NotificationPublish np = JSON.parseObject(publish.toJSONString(), NotificationPublish.class);
            wrapCommonPart(nm, np, notificationContext);
            // 处理附件类型的消息数据
            if (nm.getType() == NotificationType.ATTACHMENT.ordinal()){
                JSONObject attachmentData = attachment.getJSONObject("attachmentData");
                // 拿到封装附件数据的map
                Map<String,String> attachmentObject = JSONObject.parseObject(attachmentData.toJSONString(), new TypeReference<Map<String,String>>(){});
                notificationContext.setAttachment(attachmentObject);
            }
        }
        // 这里将会调用具体子类所实现的方法去处理该通知所需要的具体处理逻辑
        // 由于子类会配置对应的@RocketMQMessageListener注解，因此父类不需要关系消息主题
        // 只需要调用该方法，就会到对应的子类中去执行
        // 实际上父类的onMessage方法是有具体的子类先触发调用的，所以再调用该抽象方法，
        // 最终一定会回到该具体的子类中
        processingNotification(notificationContext);
    }

    /**
     * 处理由消息转换后的通知数据
     * @param notificationContext       {@link NotificationContext}
     */
    public abstract void processingNotification(NotificationContext notificationContext);


    private void wrapCommonPart(NotificationMessage nm,NotificationPublish np,CommonNotificationContext cnc){
        cnc.setContent(nm.getContent());
        cnc.setTitle(nm.getTitle());
        cnc.setConfirmable(nm.getConfirmable());
        cnc.setType(nm.getType());
        cnc.setPublishDate(nm.getPublishDate());
        cnc.setPublisher(np.getPublisher());
        cnc.setPublishTo(np.getPublishTo());
        cnc.setPublishToClass(np.getPublishToClass());
        cnc.setPublishToMembers(np.getPublishToMembers());
    }

}
