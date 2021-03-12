package sc.hqu.graduationdesign.homeworkmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author tzx
 * @date 2021-03-12 16:42
 */
@Configuration
@PropertySource(value = "classpath:application-dao.properties", encoding = "UTF-8")
public class DaoConfig {

}
