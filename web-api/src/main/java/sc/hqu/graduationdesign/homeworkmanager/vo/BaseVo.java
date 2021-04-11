package sc.hqu.graduationdesign.homeworkmanager.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author tzx
 * @date 2021-04-10 15:42
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseVo {

    // 由于配置开启全局的jackson驼峰下划线转换无法生效，因此使用继承手动配置的VO来实现

}
