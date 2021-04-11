package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;
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
     * 插入一条文件发布记录
     * @param fpe           {@link FilePublishEntity}
     */
    @Insert("insert into t_file_publish(fid,pid,publish_type) values(" +
            "#{fid},#{pid},#{publishType}" +
            ")")
    void insertFilePublish(FilePublishEntity fpe);

    /**
     * 通过文件发布id查询所有文件数据
     * @param pid           文件发布对象id
     * @param publishType   文件发布对象的类型
     * @return              {@link FileEntity}
     */
    @Select("select t_file.id,name,url from t_file,t_file_publish where t_file.id=t_file_publish.fid and pid=#{pid} and publish_type=#{publishType}")
    List<FileEntity> queryAllByPublishId(@Param("pid") Long pid, @Param("publishType") Integer publishType);

}
