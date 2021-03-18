package sc.hqu.graduationdesign.homeworkmanager.config;

import com.tencentcloudapi.common.Credential;
import lombok.Setter;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sc.hqu.graduationdesign.homeworkmanager.message.SmsNotificationHandler;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericMessagePublishProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericShortMessageSendingProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.RocketMqMessagePublisher;
import sc.hqu.graduationdesign.homeworkmanager.provider.TencentSmsSender;

/**
 * 模块自动配置文件
 * 使用了@ConditionalOnProperty(prefix = "rocketmq",value = "name-server")
 * 意味着当配置文件中没有对rocketmq的name-server属性进行配置时，是不会加载该自动配置类的
 * @author tzx
 * @date 2021-03-17 16:40
 */
@ConditionalOnProperty(prefix = "rocketmq",value = "name-server")
@Configuration
@ConfigurationProperties(prefix = "message.publish")
@Setter
public class MessagePublishAutoConfigure implements InitializingBean {

    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    private Boolean retryFlag = false;

    private Integer retryTimes = 5;

    private Long maxSpinTime = 1000L;

    private final Logger LOG = LoggerFactory.getLogger(MessagePublishAutoConfigure.class);

    /**
     * 消息发布的提供者对象
     * @return              {@link GenericMessagePublishProvider}
     */
    @Bean
    public GenericMessagePublishProvider genericMessagePublishProvider(){
        return new RocketMqMessagePublisher(rocketMqTemplate,retryFlag,retryTimes,maxSpinTime);
    }

    /**
     * 短信发送的提供者对象，当前的短信发送主要有消费对应topic的消息进行自动发送
     * 暂未接入业务逻辑层，但是为业务层提供使用
     * @return              {@link GenericShortMessageSendingProvider}
     */
    @Bean
    public GenericShortMessageSendingProvider genericShortMessageSendingProvider(){
        String secretId = System.getenv("TENCENT_SMS_ID");
        String secretKey = System.getenv("TENCENT_SMS_KEY");
        if (secretId == null || secretKey == null){
            throw new BeanCreationException("genericShortMessageSendingProvider",
                    "Tencent SMS secretId or secretKey  is not found in System env,please providing both these two params when " +
                            "creating a sms sender bean");
        }
        Credential credential = new Credential(secretId, secretKey);
        return new TencentSmsSender(credential);
    }

    /**
     * 消息消费者，当前模块仅封装了消费sms-message主题的消息者对象
     * @return              {@link SmsNotificationHandler}
     */
    @Bean
    public SmsNotificationHandler smsNotificationHandler(){
        return new SmsNotificationHandler(genericShortMessageSendingProvider());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("enable message publisher autoconfiguration.");
        if (retryFlag){
            LOG.info("using retry-times: {} | using max-spin-time: {}", retryTimes,maxSpinTime);
        }else {
            LOG.info("disable retry of message publishing");
        }
    }
}
