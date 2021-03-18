package sc.hqu.graduationdesign.homeworkmanager.exceptions;

/**
 * 短信发送异常
 * @author tzx
 * @date 2021-03-17 23:30
 */
public class MessageSendingException extends Exception{

    public MessageSendingException(String msg,Exception e){
        super(msg,e);
    }

}
