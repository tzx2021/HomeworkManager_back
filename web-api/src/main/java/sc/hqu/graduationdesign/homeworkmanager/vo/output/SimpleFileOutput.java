package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-06 23:37
 */
@Data
@ApiModel(value = "简单的文件数据对象")
public class SimpleFileOutput {

    private Long id;
    private String name;
    private String url;

}
