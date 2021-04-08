package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
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

}
