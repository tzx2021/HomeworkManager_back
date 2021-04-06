package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 1:09
 */
@Data
@ApiModel(value = "文件名更新数据输入对象")
public class UpdateFileInput {

    private Long fileId;
    private String filename;

}
