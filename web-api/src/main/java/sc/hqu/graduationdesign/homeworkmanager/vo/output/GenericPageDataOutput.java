package sc.hqu.graduationdesign.homeworkmanager.vo.output;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.hqu.graduationdesign.homeworkmanager.vo.BaseVo;

import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 1:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "通用的分页数据输出")
public class GenericPageDataOutput<T> extends BaseVo {

    private Integer total;
    private Integer pageSize;
    private Integer pageNum;
    private List<T> pageData;

}
