package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-07 1:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "文件发布数据输入对象")
public class PublishFileInput extends BaseVo {

    private Long publishId;
    private String name;
    private String url;
    private int type;

}
