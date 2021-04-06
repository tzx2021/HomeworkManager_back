package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author tzx
 * @date 2021-04-07 1:04
 */
@Data
@ApiModel(value = "分页数据查询数据输入对象")
public class PageQueryInput {

    private Integer pageSize;
    private Integer pageNum;

}
