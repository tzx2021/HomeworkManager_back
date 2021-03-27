package sc.hqu.graduationdesign.homeworkmanager.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sc.hqu.graduationdesign.homeworkmanager.annotation.Publisher;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.constant.NotificationType;
import sc.hqu.graduationdesign.homeworkmanager.exception.BusinessException;
import sc.hqu.graduationdesign.homeworkmanager.model.CommonSmsNotification;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericMessageModel;
import sc.hqu.graduationdesign.homeworkmanager.model.NotificationAttachment;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericMessagePublishProvider;
import sc.hqu.graduationdesign.homeworkmanager.util.NotificationMessageConverter;

import java.util.Map;

/**
 * 消息发送注解的切面代理逻辑封装类
 * @author tzx
 * @date 2021-03-20 9:32
 */
//@Component
//@Aspect
public class MessagePublishingAspect {

    @Autowired
    private GenericMessagePublishProvider messagePublishProvider;

    private final Logger logger = LoggerFactory.getLogger(MessagePublishingAspect.class);

    @Around("@annotation(sc.hqu.graduationdesign.homeworkmanager.annotation.Publisher)")
    public Object doAround(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Publisher publisher = signature.getMethod().getAnnotation(Publisher.class);
        NotificationType type = publisher.type();
        Object messageData = null;
        try {
            messageData = joinPoint.proceed();
            Class<?> dataClass = messageData.getClass();
            if (type.equals(NotificationType.SMS)){
                CommonSmsNotification smsNotification = null;
                if (Map.class.isAssignableFrom(dataClass)){
                    @SuppressWarnings("unchecked")
                    Map<String,String> contactMap = (Map<String, String>) messageData;
                    smsNotification = new CommonSmsNotification(contactMap);
                }else if (messageData instanceof CommonSmsNotification){
                    smsNotification = (CommonSmsNotification) messageData;
                }else {
                    throw new BusinessException(ErrorCode.MESSAGE_PUBLISH_ERROR);
                }
                doMessagePublishing(NotificationMessageConverter.convertToSmsMessage(smsNotification),publisher);
            }
        } catch (Throwable throwable) {
            logger.error("Fail to publish message",throwable);
        }
        return messageData;
    }

    private void doMessagePublishing(GenericMessageModel messageModel,Publisher publisher){

    }

}
