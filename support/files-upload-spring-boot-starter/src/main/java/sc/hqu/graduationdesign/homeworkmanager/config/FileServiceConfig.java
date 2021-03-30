package sc.hqu.graduationdesign.homeworkmanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import sc.hqu.graduationdesign.homeworkmanager.provider.FtpServiceProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericFileServiceProvider;
import javax.annotation.PostConstruct;

/**
 * @author tzx
 * @date 2021-03-29 19:12
 */
@Configuration
@ConfigurationProperties(prefix = "ftp-server")
@PropertySource(value = {"classpath:application-fileupload.properties"},encoding = "UTF-8")
public class FileServiceConfig {

    /**
     * ftp服务器文件文件访问路径前缀，
     * 例如以默认值为例，访问test.jpg的完整路径应为：http://localhost:80/homeworkmanager/uploads/test.jpg
     */
    public static String fileAccessPathPrefix = "http://localhost:80/homeworkmanager/uploads/";

    @Bean
    public GenericFileServiceProvider fileServiceProvider(){
        return new FtpServiceProvider();
    }

    public void setFileAccessPathPrefix(String fileAccessPathPrefix) {
        FileServiceConfig.fileAccessPathPrefix = fileAccessPathPrefix;
    }

    @PostConstruct
    public void printLog(){
        Logger logger = LoggerFactory.getLogger(FileServiceConfig.class);
        logger.info("File access path prefix is configured as: {}",fileAccessPathPrefix);
    }

}
