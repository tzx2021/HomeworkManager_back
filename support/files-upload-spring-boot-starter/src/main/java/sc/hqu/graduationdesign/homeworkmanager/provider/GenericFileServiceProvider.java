package sc.hqu.graduationdesign.homeworkmanager.provider;

import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.FileUploadException;

/**
 * 通用的文件服务提供者接口，目前仅支持文件上传服务，文件下载以及删除等操作会在后续进行更新
 * @author tzx
 * @date 2021-03-29 18:39
 */
public interface GenericFileServiceProvider {

    /**
     * 文件上传
     * @param file                      {@link MultipartFile}
     * @return                          文件上传在服务器中的访问文件名
     * @throws FileUploadException      文件上传时的异常
     */
    String upload(MultipartFile file) throws FileUploadException;

    /**
     * 获取文件类型
     * @param originalFilename          原始文件名
     * @return                          返回文件类型
     */
    String getType(String originalFilename);

    /**
     * 生成唯一的文件名
     * @param originalFilename          原始文件名
     * @return                          唯一的文件名，通过嵌入uuid实现
     */
    String generateUniqueFilename(String originalFilename);


}
