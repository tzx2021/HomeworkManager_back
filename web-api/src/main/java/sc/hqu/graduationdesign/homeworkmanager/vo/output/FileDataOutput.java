package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 0:32
 */
@Data
@ApiModel(value = "文件数据输出对象")
public class FileDataOutput {

    private Long id;
    private String name;
    private String type;
    private Integer publishState;
    private String uploadDate;

}
