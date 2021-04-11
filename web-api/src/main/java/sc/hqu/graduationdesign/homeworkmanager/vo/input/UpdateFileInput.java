package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-07 1:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "文件名更新数据输入对象")
public class UpdateFileInput extends BaseVo {

    private Long fileId;
    private String filename;

}
