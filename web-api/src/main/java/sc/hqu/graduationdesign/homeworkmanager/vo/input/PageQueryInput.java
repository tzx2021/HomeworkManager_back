package sc.hqu.graduationdesign.homeworkmanager.vo.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

/**
 * @author tzx
 * @date 2021-04-07 1:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "分页数据查询数据输入对象")
public class PageQueryInput extends BaseVo {

    private Integer pageSize;
    private Integer pageNum;

}
