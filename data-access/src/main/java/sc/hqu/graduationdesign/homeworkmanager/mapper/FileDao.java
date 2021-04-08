package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.FileEntity;
import java.util.List;

/**
 * 文件信息数据访问接口
 * @author tzx
 * @date 2021-03-14 23:44
 */
@Repository
public interface FileDao {

    /**
     * 根据系统账号查询该账号上传的所有文件
     * @param account       系统账号
     * @return              {@link FileEntity}
     */
    @Select("select id,publish_state,name,url,type,upload_date from t_file where account=#{account} and delete_at=0")
    List<FileEntity> queryAllByAccount(Long account);

    /**
     * 通过发布对象的id查询文件，发布对象可以是班级、课程以及通知
     * @param pid           文件发布对象的id
     * @return              {@link FileEntity}
     */
    @Select("select t_file.id,name,url from t_file,t_file_publish where t_file.id = t_file_publish.id and t_file.delete_at = 0 " +
            "and t_file_publish.id=pid")
    List<FileEntity> queryByPublishId(Long pid);

    /**
     * 插入文件信息记录
     * @param fe        {@link FileEntity}
     */
    @Insert("insert into t_file(account,publish_date,name,url,type,upload_date) values(" +
            "#{account},#{publishState},#{name},#{url},#{type},#{uploadDate}"
            + ")")
    void insertFile(FileEntity fe);

    /**
     * 更新文件的发布状态为已发布
     * @param fid       文件id
     */
    @Update("update t_file set publish_state=1 where id=#{fid}")
    void updatePublishStateById(Long fid);

    /**
     * 更新文件名称
     * @param newFileName       新的文件名
     * @param fid               文件id
     */
    @Update("update t_file set name=#{newFileName} where id=#{fid}")
    void updateFileNameById(@Param("newFileName") String newFileName, @Param("fid") Long fid);

    /**
     * 通过文件id删除文件
     * @param fid       文件id
     * @param deleteAt  删除时间戳
     */
    @Update("update t_file set delete_at=#{deleteAt} where fid=#{fid}")
    void deleteFileById(@Param("fid") Long fid,@Param("deleteAt") Long deleteAt);

}
