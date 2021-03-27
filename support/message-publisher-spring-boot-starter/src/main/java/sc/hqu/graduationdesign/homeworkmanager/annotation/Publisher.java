package sc.hqu.graduationdesign.homeworkmanager.annotation;

import sc.hqu.graduationdesign.homeworkmanager.constant.NotificationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息队列的基于注解的消息发布功能
 * 可以通过在业务方法上使用该注解来进行消息的发布，
 * 通过AOP的特性降低与业务的耦合
 * 使用该注解进行消息发布时，要求方法返回值必须是封装消息发布的数据对象
 * @author tzx
 * @date 2021-03-20 9:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Publisher {

    /**
     * 通知发送的类型
     * @return      {@link NotificationType}
     */
    NotificationType type();

    /**
     * 是否发送异步消息，默认使用异步发送
     * @return      true代表使用异步发送，false使用同步发送
     */
    boolean asyncSend() default true;

    /**
     * 批量发送消息，默认为单发消息
     * @return      true代表批量发送，false代表单发
     */
    boolean sendBatch() default false;

}
