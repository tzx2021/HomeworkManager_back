package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 1:07
 */
@Data
@ApiModel(value = "文件发布数据输入对象")
public class PublishFileInput {

    private Long fileId;
    private Long publishId;

}
