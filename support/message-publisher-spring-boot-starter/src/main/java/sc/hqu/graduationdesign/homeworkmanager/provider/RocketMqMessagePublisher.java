package sc.hqu.graduationdesign.homeworkmanager.provider;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.MessagePublishingException;
import sc.hqu.graduationdesign.homeworkmanager.model.AsyncMessageCallback;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericMessageModel;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * RocketMQ消息发布对象
 * @author tzx
 * @date 2021-03-17 13:22
 */
public class RocketMqMessagePublisher implements GenericMessagePublishProvider {

    private final RocketMQTemplate rocketMqTemplate;

    /**重试标识，默认不重试*/
    private final Boolean retryFlag;

    /**重试次数，默认是5*/
    private final int retryTimes;

    /**等待回调的最大自旋时间，单位是ms，默认是1000*/
    private final Long maxSpinTime;


    private static final Logger LOG = LoggerFactory.getLogger(RocketMqMessagePublisher.class);

    public RocketMqMessagePublisher(RocketMQTemplate rocketMqTemplate, @Nullable Boolean retryFlag,
                                    @Nullable Integer retryTimes, @Nullable Long maxSpinTime){
        this.rocketMqTemplate = rocketMqTemplate;
        this.retryFlag = retryFlag != null ? retryFlag : false;
        this.retryTimes = retryTimes != null ? retryTimes : 5;
        this.maxSpinTime = maxSpinTime != null ? maxSpinTime : 1000;
    }

    @Override
    public boolean publishSync(GenericMessageModel messageModel) throws MessagePublishingException {
        List<GenericMessageModel> messageModelList = new ArrayList<>(1);
        return batchPublishSync(messageModelList);
    }

    @Override
    public void publishAsync(GenericMessageModel messageModel) {
        sendAsync(messageModel);
    }

    @Override
    public boolean batchPublishSync(List<GenericMessageModel> messageModelList) throws MessagePublishingException {
        return sendSync(messageModelList);
    }

    private boolean sendSync(List<GenericMessageModel> messageModelList) throws MessagePublishingException {
        DefaultMQProducer mqProducer = rocketMqTemplate.getProducer();
        List<Message> messageList = new ArrayList<>(messageModelList.size());
        messageModelList.forEach(messageModel -> {
            Message message = new Message(messageModel.getTopic(), messageModel.getTag(),
                    messageModel.getJsonData().getBytes(StandardCharsets.UTF_8));
            messageList.add(message);
        });
        return doSyncMessageSend(mqProducer, messageList);
    }

    private void sendAsync(GenericMessageModel messageModel){
        AsyncMessageCallback callback = new AsyncMessageCallback();
        DefaultMQProducer mqProducer = rocketMqTemplate.getProducer();
        Message message = new Message(messageModel.getTopic(), messageModel.getTag(),
                messageModel.getJsonData().getBytes(StandardCharsets.UTF_8));
        doAsyncMessageSend(mqProducer, message, callback);
        // 手写对普通消息的重发机制
        if (retryFlag){
            long start = System.currentTimeMillis();
            int retryCount = retryTimes;
            // 利用自旋在设置的最大自旋时间内等待回调结果
            while (true) {
                long curr = System.currentTimeMillis();
                // 如果在最大自旋时间内仍没有耗尽重发次数，那么也会结束自旋
                if ((curr - start) < maxSpinTime) {
                    if (callback.getIsOk() != null) {
                        if (!callback.getIsOk() && retryCount > 0) {
                            // 消息重发前，将callback的状态重置
                            callback.resetStatus();
                            doAsyncMessageSend(mqProducer, message, callback);
                            --retryCount;
                        } else {
                            if (retryCount < retryTimes){
                                LOG.info("Retry sending message success");
                            }
                            break;
                        }
                    }
                }else {
                    break;
                }
            }
        }
    }

    private boolean doSyncMessageSend(DefaultMQProducer mqProducer, List<Message> messageList) throws MessagePublishingException {
        try {
            SendResult sendResult = mqProducer.send(messageList);
            return sendResult.getSendStatus().equals(SendStatus.SEND_OK);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new MessagePublishingException("RocketMQ message send fail", e);
        }
    }

    private void doAsyncMessageSend(DefaultMQProducer mqProducer, Message message, SendCallback sendCallback) {
        try {
            mqProducer.send(message, sendCallback);
        } catch (MQClientException | RemotingException | InterruptedException e) {
            printError(e);
        }
    }

    private void printError(Exception e){
        LOG.error("RocketMQ message send fail: {}",e.getMessage(),e);
    }

}
