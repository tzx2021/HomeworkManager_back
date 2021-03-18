package sc.hqu.graduationdesign.homeworkmanager.provider;

import sc.hqu.graduationdesign.homeworkmanager.exceptions.MessagePublishingException;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericMessageModel;

import java.util.List;

/**
 * 通用的消息发布提供者接口，用于消息队列的消息发送
 * @author tzx
 * @date 2021-03-17 13:30
 */
public interface GenericMessagePublishProvider {

    /**
     * 发送同步消息，同步消息不支持重发
     * 重发可以在业务逻辑层自行实现
     * @param messageModel              {@link GenericMessageModel}
     * @return                          true代表发送成功
     * @throws MessagePublishingException  消息发送异常
     */
    boolean publishSync(GenericMessageModel messageModel) throws MessagePublishingException;

    /**
     * 异步方式发送消息
     * @param messageModel      {@link GenericMessageModel}
     */
    void publishAsync(GenericMessageModel messageModel);

    /**
     * 同步方式进行消息批量发布，不支持重发
     * @param messageModelList          {@link GenericMessageModel}
     * @return                          同步发送的结果，true代表发送成功，false代表发送失败
     * @throws MessagePublishingException  消息发送异常
     */
    boolean batchPublishSync(List<GenericMessageModel> messageModelList) throws MessagePublishingException;

}
