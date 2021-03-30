package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.FileService;
import sc.hqu.graduationdesign.homeworkmanager.entity.FileEntity;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.FileUploadException;
import sc.hqu.graduationdesign.homeworkmanager.mapper.FileDao;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericFileServiceProvider;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;

/**
 * @author tzx
 * @date 2021-03-30 13:11
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private GenericFileServiceProvider fileServiceProvider;

    @Autowired
    private FileDao fileDao;

    @Override
    public boolean upload(MultipartFile file) {
        try {
            // 原始的文件名称是保存到数据库中的
            String originalFilename = file.getOriginalFilename();
            // 文件url
            String fileAccessUrl = fileServiceProvider.upload(file);
            if (fileAccessUrl != null){
                String type = fileServiceProvider.getType(originalFilename);
                FileEntity fileEntity = new FileEntity();
                // 通过security的上下文对象获取当前登录用户的用户名，即这里需要的account属性
                Long account = Long.getLong(SecurityContextUtil.userDetails().getUsername());
                fileEntity.setAccount(account);
                fileEntity.setName(originalFilename);
                fileEntity.setType(type);
                fileEntity.setUploadDate(System.currentTimeMillis());
                fileEntity.setUrl(fileAccessUrl);
                // 将文件记录保存到数据库中
                fileDao.insertFile(fileEntity);
            }else {
                return false;
            }
        }catch (FileUploadException e){
            // fileServiceProvider已经将日志详细记录，这里不再需要处理异常的信息
            return false;
        }
        return true;
    }

    @Override
    public boolean download(String sourcePath, String downloadPath) {
        return false;
    }

    @Override
    public boolean deleteFile(Long id, boolean deleteFromServer) {
        return false;
    }

    @Override
    public void updateFilename(Long id, String filename) {

    }

    @Override
    public void publishFile(Long fid, Long pid) {

    }
}
