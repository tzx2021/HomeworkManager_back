package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件服务接口
 * @author tzx
 * @date 2021-03-30 12:56
 */
public interface FileService {

    // TODO: 2021/3/30 listFile,需要dto

    /**
     * 文件上传
     * @param file      {@link MultipartFile}
     * @return          文件上传结果，true表示上传成功，false表示上传失败
     */
    boolean upload(MultipartFile file);

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
     * @return                  删除结果，true代表删除成功，false为删除失败
     */
    boolean deleteFile(Long id,boolean deleteFromServer);

    /**
     * 更新文件名称
     * @param id                文件id
     * @param filename          修改的文件名称
     */
    void updateFilename(Long id,String filename);

    /**
     * 发布文件
     * @param fid               文件id
     * @param pid               发布对象id集合，可以是班级id或课程id或通知id
     */
    void publishFile(Long fid, Long pid);

}
