package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.FilePublishDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.SimpleFileDataDto;
import sc.hqu.graduationdesign.homeworkmanager.entity.FilePublishEntity;

import java.util.List;

/**
 * 文件服务接口
 * @author tzx
 * @date 2021-03-30 12:56
 */
public interface FileService {


    /**
     * 获取文件分页数据
     * @param account       账号
     * @param pageSize      分页大小
     * @param pageNum       分页索引
     * @return              分页数据
     */
    Object getFilePageData(Long account,int pageSize,int pageNum);

    /**
     * 文件上传
     * @param file      {@link MultipartFile}
     * @return          文件上传结果，true表示上传成功，false表示上传失败
     */
    String upload(MultipartFile file);

    /**
     * 文件下载，该服务会在后续的版本迭代中进行更新
     * @param sourcePath        被下载的资源在ftp服务器中路径
     * @param downloadPath      资源的下载路径
     * @return                  文件下载结果，true表示下载成功，false表示下载失败
     */
    boolean download(String sourcePath,String downloadPath);

    /**
     * 文件删除，当前仅提供数据库中文件记录的删除，文件对应的服务器资源删除会在后续的版本迭代中更新
     * @param id                文件在数据库中的id
     * @param deleteFromServer  是否从服务器上删除文件资源，默认为false
     */
    void deleteFile(Long id, boolean deleteFromServer);

    /**
     * 更新文件名称
     * @param id                文件id
     * @param filename          修改的文件名称
     */
    void updateFilename(Long id,String filename);

    /**
     * 批量发布文件
     * @param filePublishDtoList    文件创建和发布的数据封装对象集合
     * @param publish               文件发布状态，1代表会发布文件，0则不发布文件
     */
    void batchCreate(List<FilePublishDto> filePublishDtoList,int publish);

    /**
     * 创建并且发布一个文件记录
     * @param filePublishDto        文件创建和发布的数据封装对象
     * @param publish               文件发布状态，1代表会发布文件，0则不发布文件
     */
    void create(FilePublishDto filePublishDto,int publish);

}
