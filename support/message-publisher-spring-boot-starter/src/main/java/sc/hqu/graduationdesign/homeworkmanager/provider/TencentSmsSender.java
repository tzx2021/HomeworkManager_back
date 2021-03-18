package sc.hqu.graduationdesign.homeworkmanager.provider;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.MessageSendingException;

/**
 * @author tzx
 * @date 2021-03-17 22:58
 */
public class TencentSmsSender implements GenericShortMessageSendingProvider{

    private final SmsClient tencentSmsClient;

    private static final String APP_ID="1400379887";

    /** 已申请的短信签名*/
    private static final String SING="wtmsystem";

    /**
     * arr[0]是学生提醒短信的模板，arr[1]是家长提醒短信的模板
     * 目前腾讯云只支持使用控制台模板进行短信发送，无法发送自定义内容的短信
     */
    private static final String[] TEMPLATE_INDEX = {"895874","895876"};

    private static final Logger LOG = LoggerFactory.getLogger(TencentSmsSender.class);

    public TencentSmsSender(Credential tencentSmsCredential){
        this.tencentSmsClient = new SmsClient(tencentSmsCredential, "ap-guangzhou");
    }

    @Override
    public void sendingMessage(String phoneNum, String singleParam, int templateIndex) throws MessageSendingException {
        // 没有使用正确模板的调用不进行发送
        if (templateIndex < 0 || templateIndex > 1){
            LOG.error("Wrong sms template index, message sending has been canceled!");
            return;
        }
        String[] phoneNumArr = {"+86"+phoneNum};
        String[] paramArr = {singleParam};
        // 新建短信发送请求，使用的版本要和smsClient的一致
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        // 短信控制台的应用id
        sendSmsRequest.setSmsSdkAppid(APP_ID);
        // 设置短信签名
        sendSmsRequest.setSign(SING);
        // 设置发送号码
        sendSmsRequest.setPhoneNumberSet(phoneNumArr);
        // 设置选用的模板序号
        sendSmsRequest.setTemplateID(TEMPLATE_INDEX[templateIndex]);
        // 设置模板参数
        sendSmsRequest.setTemplateParamSet(paramArr);
        try {
            tencentSmsClient.SendSms(sendSmsRequest);
        } catch (TencentCloudSDKException e) {
            throw new MessageSendingException("Sms sending fail with tencent sms", e);
        }
    }

}
