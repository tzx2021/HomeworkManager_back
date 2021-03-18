package sc.hqu.graduationdesign.homeworkmanager.exceptions;

/**
 * 消息队列中的消息发布异常
 * @author tzx
 * @date 2021-03-17 13:49
 */
public class MessagePublishingException extends Throwable{

    public MessagePublishingException(String msg, Exception e){
        super(msg,e);
    }

}
