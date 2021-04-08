package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sc.hqu.graduationdesign.homeworkmanager.constant.NotificationType;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.MemberNotifyDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.NotificationCreateDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.SimpleFileDataDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.FileService;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.NotificationService;
import sc.hqu.graduationdesign.homeworkmanager.entity.FilePublishEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.NotificationEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.NotificationPublishEntity;
import sc.hqu.graduationdesign.homeworkmanager.mapper.FilePublishDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.NotificationDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.NotificationPublishDao;
import sc.hqu.graduationdesign.homeworkmanager.model.*;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericMessagePublishProvider;
import sc.hqu.graduationdesign.homeworkmanager.util.NotificationMessageConverter;
import vinfer.learnjava.queryhelper.annotation.QueryHelper;
import vinfer.learnjava.queryhelper.constant.InterceptMode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tzx
 * @date 2021-04-07 22:04
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private NotificationPublishDao notificationPublishDao;

    @Autowired
    private GenericMessagePublishProvider messagePublishProvider;

    @Autowired
    private FilePublishDao filePublishDao;

    @QueryHelper(mapperClass = NotificationDao.class,interceptMode = InterceptMode.MODIFY_RESULT)
    @Override
    public Object getNotificationPageData(Long account, int pageSize, int pageNum) {
        return notificationDao.queryAllByAccount(account);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public NotificationCreateDto create(NotificationCreateDto dto,Long account) {
        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(dto,notificationEntity);
        notificationEntity.setAccount(account);
        List<NotificationCreateDto.SimpleContactData> contactDataList = dto.getContactDataList();
        List<NotificationPublishEntity> notificationPublishEntities = new ArrayList<>(contactDataList.size());

        Long publishDate = Calendar.getInstance().getTimeInMillis();
        notificationEntity.setCreateDate(publishDate);
        // 保存通知记录
        notificationDao.insertNotification(notificationEntity);
        Long nid = notificationEntity.getId();
        dto.setId(nid);
        // 保存通知发布记录
        List<Long> publishIdList = dto.getContactDataList().stream().map(NotificationCreateDto.SimpleContactData::getPublishId).collect(Collectors.toList());
        publishIdList.forEach(pid -> {
            NotificationPublishEntity notificationPublishEntity = new NotificationPublishEntity();
            notificationPublishEntity.setNid(nid);
            notificationPublishEntity.setPid(pid);
            notificationPublishEntities.add(notificationPublishEntity);
        });
        notificationPublishDao.insertNotificationPublish(notificationPublishEntities);

        // 消息推送的公共部分的数据封装
        NotificationMessage message = new NotificationMessage();
        BeanUtils.copyProperties(notificationEntity,message);
        message.setPublishDate(publishDate);
        NotificationPublish publish = new NotificationPublish();
        publish.setPublishTo(contactDataList.stream().map(NotificationCreateDto.SimpleContactData::getPublishId).collect(Collectors.toList()));
        publish.setPublisher(account);
        String memberClass = "class";
        if (dto.getMemberType().equals(memberClass)){
            publish.setPublishToClass(true);
        }else {
            publish.setPublishToMembers(true);
        }
        // 处理消息推送
        handleMessagePushing(dto,publish,message);
        return dto;
    }

    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public void handleMessagePushing(NotificationCreateDto dto,NotificationPublish publish,NotificationMessage message){
        // 按照类型进行消息推送
        int type = dto.getType();
        switch (type){
            // 处理文本通知
            case 0:
                // 推送文本通知
                CommonTextNotification textNotification = new CommonTextNotification(publish,message);
                messagePublishProvider.publishAsync(NotificationMessageConverter.convertToTextMessage(textNotification));
                break;
            // 处理附件通知
            case 1:
                CommonAttachmentNotification attachmentNotification = new CommonAttachmentNotification();
                attachmentNotification.setMessage(message);
                attachmentNotification.setPublish(publish);
                // 生成附件数据
                NotificationAttachment attachment = new NotificationAttachment();
                Map<String,String> attachmentData = new HashMap<>(8);
                List<SimpleFileDataDto> attachments = dto.getAttachments();
                // 生成文件通知关联记录
                List<FilePublishEntity> filePublishEntities = new ArrayList<>(attachments.size());
                attachments.forEach(simpleFileDataDto -> {
                    attachmentData.put(simpleFileDataDto.getUrl(),simpleFileDataDto.getName());
                    FilePublishEntity fpe = new FilePublishEntity();
                    fpe.setFid(simpleFileDataDto.getId());
                    fpe.setPid(dto.getId());
                });
                // 推送附件通知
                attachment.setAttachmentData(attachmentData);
                attachmentNotification.setAttachment(attachment);
                messagePublishProvider.publishAsync(NotificationMessageConverter.convertToAttachmentMessage(attachmentNotification));
                // 保存文件通知的关联记录
                filePublishDao.batchInsertFilePublish(filePublishEntities);
                break;
            // 短信通知
            case 2:
                List<NotificationCreateDto.SimpleContactData> contactDataList = dto.getContactDataList();
                int size = (int)(contactDataList.size() / 0.75);
                Map<String,String> contactMap = new HashMap<>(size);
                String studentType = "student";
                contactDataList.forEach(simpleContactData -> {
                    String contact = simpleContactData.getContact();
                    if (dto.getMemberType().equals(studentType)){
                        // 约定了如果推送的对象是学生，那么需要对号码加上S前缀
                        contact = "S_"+contact;
                    }
                    contactMap.put(contact,simpleContactData.getName());
                });
                CommonSmsNotification smsNotification = new CommonSmsNotification(contactMap);
                messagePublishProvider.publishAsync(NotificationMessageConverter.convertToSmsMessage(smsNotification));
                break;
            default:break;
        }
    }

    @Override
    public void notifyMember(MemberNotifyDto dto) {

    }
}
