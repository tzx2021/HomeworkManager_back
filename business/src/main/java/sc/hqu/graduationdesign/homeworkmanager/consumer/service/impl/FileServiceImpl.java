package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.FilePublishDto;
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

import java.util.ArrayList;
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
    public String upload(MultipartFile file) {
        // 文件url
        String fileAccessUrl = fileServiceProvider.upload(file);
        if (fileAccessUrl != null){
            return fileAccessUrl;
        }else {
            throw new FileUploadException("Fail to Uploaded file!");
        }
    }

    @Override
    public boolean download(String sourcePath, String downloadPath) {
        return false;
    }

    @Override
    public void deleteFile(Long id, boolean deleteFromServer) {
        fileDao.deleteFileById(id, Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public void updateFilename(Long id, String filename) {
        fileDao.updateFileNameById(filename,id);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public void batchCreate(List<FilePublishDto> filePublishDtoList, int publishState) {
        List<FilePublishEntity> filePublishEntities = new ArrayList<>();
        if (filePublishDtoList.size() > 1){
            filePublishDtoList.forEach(filePublishDto -> {
                String url = filePublishDto.getUrl();
                String name = filePublishDto.getName();
                FileEntity fileEntity = processFileEntity(name,url,publishState);
                fileDao.insertFile(fileEntity);
                if (publishState == 1){
                    FilePublishEntity fpe = new FilePublishEntity();
                    fpe.setFid(fileEntity.getId());
                    fpe.setPid(filePublishDto.getPid());
                    fpe.setPublishType(filePublishDto.getType());
                    filePublishEntities.add(fpe);
                }
            });
            if (filePublishEntities.size() > 0){
                filePublishDao.batchInsertFilePublish(filePublishEntities);
            }
        }else if (filePublishDtoList.size() == 1){
            create(filePublishDtoList.get(0),publishState);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public void create(FilePublishDto filePublishDto,int publish) {
        System.out.println(filePublishDto);
        FileEntity fileEntity = processFileEntity(filePublishDto.getName(),filePublishDto.getUrl(),publish);
        fileDao.insertFile(fileEntity);
        if (publish == 1){
            FilePublishEntity fpe = new FilePublishEntity();
            fpe.setPid(filePublishDto.getPid());
            fpe.setFid(fileEntity.getId());
            fpe.setPublishType(filePublishDto.getType());
            filePublishDao.insertFilePublish(fpe);
        }
    }

    private FileEntity processFileEntity(String filename,String url,int publish){
        FileEntity fileEntity = new FileEntity();
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        fileEntity.setAccount(account);
        fileEntity.setName(filename);
        fileEntity.setUrl(url);
        fileEntity.setUploadDate(Calendar.getInstance().getTimeInMillis());
        fileEntity.setType(getFileType(url));
        // 由于需要发布文件，因此直接设置发布状态为1
        fileEntity.setPublishState(publish);
        return fileEntity;
    }

    private String getFileType(String fileUrl){
        // 获取文件类型
        String[] parts = fileUrl.split("\\.");
        return parts[parts.length - 1];
    }

}
