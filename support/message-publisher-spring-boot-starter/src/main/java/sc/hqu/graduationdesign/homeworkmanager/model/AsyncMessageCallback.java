package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tzx
 * @date 2021-03-17 14:54
 */
@Getter
public class AsyncMessageCallback implements SendCallback {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncMessageCallback.class);

    private Boolean isOk = null;

    public void resetStatus(){
        this.isOk = null;
    }


    @Override
    public void onSuccess(SendResult sendResult) {
        SendStatus sendStatus = sendResult.getSendStatus();
        if (sendStatus.equals(SendStatus.SEND_OK)){
            this.isOk = true;
            LOG.info("message-{} send successfully",sendResult.getMsgId());
        }else {
            this.isOk = false;
            LOG.error("message send is not ok,the sending result as follow:{}",sendResult);
        }
    }

    @Override
    public void onException(Throwable throwable) {
        this.isOk = false;
        LOG.error("message send is fail,the error msg as follow:{}",throwable.getMessage(),throwable);
    }
}
