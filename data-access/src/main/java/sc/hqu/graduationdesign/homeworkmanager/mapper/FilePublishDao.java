package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.FileEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.FilePublishEntity;

import java.util.List;

/**
 * @author tzx
 * @date 2021-03-17 0:44
 */
@Repository
public interface FilePublishDao {

    /**
     * 批量插入文件发布信息记录
     * @param filePublishEntities       {@link FilePublishEntity}
     */
    void batchInsertFilePublish(@Param("filePublishEntities")List<FilePublishEntity> filePublishEntities);

    /**
     * 通过文件发布id查询所有文件数据
     * @param pid       文件发布对象id
     * @return          {@link FileEntity}
     */
    @Select("select t_file.id,name,url from t_file inner join t_file_publish on id where pid=#{pid}")
    List<FileEntity> queryAllByPublishId(Long pid);

}
