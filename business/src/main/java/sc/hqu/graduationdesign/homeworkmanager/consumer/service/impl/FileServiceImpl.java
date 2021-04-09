package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.SimpleFileDataDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.FileService;
import sc.hqu.graduationdesign.homeworkmanager.entity.FileEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.FilePublishEntity;
import sc.hqu.graduationdesign.homeworkmanager.exceptions.FileUploadException;
import sc.hqu.graduationdesign.homeworkmanager.mapper.FileDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.FilePublishDao;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericFileServiceProvider;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
import vinfer.learnjava.queryhelper.annotation.QueryHelper;
import vinfer.learnjava.queryhelper.constant.InterceptMode;

import java.util.Calendar;
import java.util.List;

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

    @Autowired
    private FilePublishDao filePublishDao;

    @QueryHelper(mapperClass = FileDao.class,interceptMode = InterceptMode.MODIFY_RESULT)
    @Override
    public Object getFilePageData(Long account, int pageSize, int pageNum) {
        return fileDao.queryAllByAccount(account);
    }

    @Override
    public SimpleFileDataDto upload(MultipartFile file) {
        SimpleFileDataDto simpleFile = new SimpleFileDataDto();
        String originalFilename = file.getOriginalFilename();
        // 文件url
        String fileAccessUrl = fileServiceProvider.upload(file);
        if (fileAccessUrl != null){
            String type = fileServiceProvider.getType(originalFilename);
            // 通过security的上下文对象获取当前登录用户的用户名，即这里需要的account属性
            Long account = Long.getLong(SecurityContextUtil.userDetails().getUsername());
            FileEntity fileEntity = new FileEntity();
            fileEntity.setAccount(account);
            fileEntity.setName(originalFilename);
            fileEntity.setType(type);
            fileEntity.setUploadDate(System.currentTimeMillis());
            fileEntity.setUrl(fileAccessUrl);
            // 将文件记录保存到数据库中
            fileDao.insertFile(fileEntity);
            BeanUtils.copyProperties(fileEntity,simpleFile);
        }else {
            throw new FileUploadException("Fail to Uploaded file!");
        }
        return simpleFile;
    }

    @Override
    public boolean download(String sourcePath, String downloadPath) {
        return false;
    }

    @Override
    public boolean deleteFile(Long id, boolean deleteFromServer) {
        fileDao.deleteFileById(id, Calendar.getInstance().getTimeInMillis());
        return true;
    }

    @Override
    public void updateFilename(Long id, String filename) {
        fileDao.updateFileNameById(filename,id);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public void publishFiles(List<FilePublishEntity> filePublishEntities) {
        filePublishDao.batchInsertFilePublish(filePublishEntities);
    }
}
