package sc.hqu.graduationdesign.homeworkmanager.provider;

import sc.hqu.graduationdesign.homeworkmanager.exceptions.MessageSendingException;

/**
 * 通用的短信发送提供者接口
 * @author tzx
 * @date 2021-03-17 19:38
 */
public interface GenericShortMessageSendingProvider {

    /**
     * 内定模板的消息发送
     * @param phoneNum                  手机号
     * @param singleParam               短信参数
     * @param templateIndex             模板索引
     * @throws MessageSendingException  短信发送异常
     */
    void sendingMessage(String phoneNum,String singleParam,String templateIndex) throws MessageSendingException;

}
