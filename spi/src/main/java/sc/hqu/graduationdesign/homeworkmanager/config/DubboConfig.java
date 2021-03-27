package sc.hqu.graduationdesign.homeworkmanager.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author tzx
 * @date 2021-03-19 2:31
 */
@Configuration
@ConditionalOnProperty(value = "dubbo.enable",havingValue = "true",matchIfMissing = true)
@PropertySource(value = "classpath:application-dubbo.properties",encoding = "UTF-8")
public class DubboConfig {

    /**
     * dubbo应用配置
     * @return          {@link ApplicationConfig}
     */
    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("homeworkmanager-teacher-server-end");
        applicationConfig.setOwner("tzx");
        return applicationConfig;
    }

    /**
     * dubbo注册中心配置
     * @return          {@link RegistryConfig}
     */
    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        // 使用zookeeper作为注册中心
        registryConfig.setProtocol("zookeeper");
        // 从环境变量中读取zookeeper的服务地址
        registryConfig.setAddress(System.getenv("ZK_SERVER"));
        return registryConfig;
    }

    /**
     * dubbo的协议配置
     * @return          {@link ProtocolConfig}
     */
    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        return protocolConfig;
    }

}
