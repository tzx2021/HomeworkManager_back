package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sc.hqu.graduationdesign.homeworkmanager.constant.FilePublishType;
import sc.hqu.graduationdesign.homeworkmanager.constant.MemberType;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.*;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.FileService;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.NotificationService;
import sc.hqu.graduationdesign.homeworkmanager.entity.FilePublishEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.NotificationEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.NotificationPublishEntity;
import sc.hqu.graduationdesign.homeworkmanager.mapper.*;
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

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private FileService fileService;

    @QueryHelper(mapperClass = NotificationDao.class,interceptMode = InterceptMode.MODIFY_RESULT)
    @Override
    public Object getNotificationPageData(Long account, int pageSize, int pageNum) {
        return notificationDao.queryAllByAccount(account);
    }

    @Override
    public List<NotificationMemberDto> getMemberDataById(Long notificationId) {
        List<NotificationPublishEntity> npeList = notificationPublishDao.queryByNotificationId(notificationId);
        List<NotificationMemberDto> notificationMemberDtoList = new ArrayList<>(npeList.size());
        if (npeList.size() > 0){
            NotificationPublishEntity npe = npeList.get(0);
            String memberType = npe.getMemberType();
            if (memberType.equals(MemberType.CLASS.getName())){
                // 查询班级所有的学生
                List<ClassStudentView> classStudentViews = new ArrayList<>();
                npeList.forEach(pe -> {
                    List<ClassStudentView> viewList = studentDao.querySimpleInfoByClassIdInView(pe.getPid()).stream()
                            .filter(classStudentView -> {
                                // 基于学号进行去重过滤
                                List<Long> stuNoList = classStudentViews.stream()
                                        .map(ClassStudentView::getStudentNo)
                                        .collect(Collectors.toList());
                                return !stuNoList.contains(classStudentView.getStudentNo());
                            }).collect(Collectors.toList());
                    classStudentViews.addAll(viewList);
                });
                // 遍历赋值
                classStudentViews.forEach(classStudentView -> {
                    NotificationMemberDto memberDto = new NotificationMemberDto();
                    BeanUtils.copyProperties(classStudentView,memberDto);
                    // 将成员设置为学生
                    memberDto.setMemberType("学生");
                    notificationMemberDtoList.add(memberDto);
                });
            }else if (memberType.equals(MemberType.STUDENT.getName())){
                System.out.println(npeList);
                // 通过学生id查询学生信息
                List<ClassStudentView> viewList = studentDao.querySimpleInfoByStudentNoListInView(
                        npeList.stream().map(NotificationPublishEntity::getPid).collect(Collectors.toList()));
                viewList.forEach(classStudentView -> {
                    NotificationMemberDto memberDto = new NotificationMemberDto();
                    BeanUtils.copyProperties(classStudentView,memberDto);
                    memberDto.setMemberType("学生");
                    notificationMemberDtoList.add(memberDto);
                });
            }else {
                // 通知的对象是学生家长
                List<ClassStudentParentView> parentViews = parentDao.queryStudentParentByParentIdInView(
                        npeList.stream().map(NotificationPublishEntity::getPid).collect(Collectors.toList()));
                parentViews.forEach(classStudentParentView -> {
                    NotificationMemberDto memberDto = new NotificationMemberDto();
                    BeanUtils.copyProperties(classStudentParentView,memberDto);
                    memberDto.setMemberType(classStudentParentView.getStudentName()+"家长");
                    memberDto.setClassName("———");
                    memberDto.setStudentNo(0L);
                    notificationMemberDtoList.add(memberDto);
                });
            }
        }
        return notificationMemberDtoList;
    }

    @Override
    public List<SimpleFileDataDto> getFileDataById(Long notificationId) {
        return filePublishDao.queryAllByPublishId(notificationId, FilePublishType.NOTIFICATION.getType()).stream()
                .map(fileEntity -> new SimpleFileDataDto(fileEntity.getId(),fileEntity.getName(),fileEntity.getUrl()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public NotificationOutputDto create(NotificationCreateDto dto, Long account) {

        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(dto,notificationEntity);
        notificationEntity.setAccount(account);
        List<NotificationCreateDto.SimpleContactData> contactDataList = dto.getContactDataList();
        List<NotificationPublishEntity> notificationPublishEntities = new ArrayList<>(contactDataList.size());

        Long publishDate = Calendar.getInstance().getTimeInMillis();
        notificationEntity.setCreateDate(publishDate);

        List<Long> publishIdList;
        // 如果通知对象是班级，那么转换为student，并且查询该课程选课的所有学生
        if (MemberType.COURSE.getName().equals(dto.getMemberType())){
            publishIdList = handleCourseElectionStudent(dto.getContactDataList().get(0).getPublishId());
            // 转换为student
            dto.setMemberType(MemberType.STUDENT.getName());
        }else {
            publishIdList = dto.getContactDataList().stream().map(NotificationCreateDto.SimpleContactData::getPublishId).collect(Collectors.toList());
        }
        String memberType = dto.getMemberType();
        notificationEntity.setMemberType(memberType);
        // 保存通知记录
        notificationDao.insertNotification(notificationEntity);
        Long nid = notificationEntity.getId();
        dto.setId(nid);
        // 保存通知发布记录
        publishIdList.forEach(pid -> {
            NotificationPublishEntity notificationPublishEntity = new NotificationPublishEntity();
            notificationPublishEntity.setNid(nid);
            notificationPublishEntity.setPid(pid);
            notificationPublishEntity.setMemberType(memberType);
            notificationPublishEntities.add(notificationPublishEntity);
        });
        notificationPublishDao.batchInsert(notificationPublishEntities);

        // 消息推送的公共部分的数据封装
        NotificationMessage message = new NotificationMessage();
        // 数字转布尔
        message.setConfirmable(notificationEntity.getConfirmable() == 1);
        BeanUtils.copyProperties(notificationEntity,message);
        message.setPublishDate(publishDate);
        NotificationPublish publish = new NotificationPublish();
        publish.setPublishTo(publishIdList);
        publish.setPublisher(account);
        String memberClass = "class";
        if (memberType.equals(memberClass)){
            publish.setPublishToClass(true);
        }else {
            publish.setPublishToMembers(true);
        }
        // 处理消息推送
        handleMessagePushing(dto,publish,message);
        NotificationOutputDto outputDto = new NotificationOutputDto();
        BeanUtils.copyProperties(notificationEntity,outputDto);
        outputDto.setConfirmCount(contactDataList.size());
        outputDto.setMemberType(memberType);
        return outputDto;
    }

    private List<Long> handleCourseElectionStudent(Long courseId){
        return studentCourseDao.queryStudentCourseElectionByCourseId(courseId).stream()
                .map(StudentCourseElectionView::getStudentNo)
                .collect(Collectors.toList());
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
                List<FilePublishDto> attachments = dto.getAttachments();
                attachments.forEach(att -> {
                    attachmentData.put(att.getUrl(),att.getName());
                    att.setPid(dto.getId());
                    att.setType(3);
                });
                // 对文件记录进行创建并发布，publish为1代表要发布文件
                fileService.batchCreate(attachments,1);

                // 推送附件通知
                attachment.setAttachmentData(attachmentData);
                attachmentNotification.setAttachment(attachment);
                messagePublishProvider.publishAsync(NotificationMessageConverter.convertToAttachmentMessage(attachmentNotification));
                break;
            // 短信通知
            case 2:
                List<NotificationCreateDto.SimpleContactData> contactDataList = dto.getContactDataList();
                pushingSms(contactDataList, dto.getMemberType(), dto.getContent());
                break;
            default:break;
        }
    }

    private void pushingSms(List<NotificationCreateDto.SimpleContactData> contactDataList,
                            String memberType, @Nullable String content){
        int size = (int)(contactDataList.size() / 0.75);
        Map<String,String> contactMap = new HashMap<>(size);
        String studentType = "student";
        contactDataList.forEach(simpleContactData -> {
            String contact = simpleContactData.getContact();
            // content为空时推送短信提醒，此时需要对学生的号码进行处理
            if (content == null && memberType.equals(studentType)){
                // 约定了如果推送的对象是学生，那么需要对号码加上S前缀
                contact = "S_"+contact;
            }
            contactMap.put(contact,simpleContactData.getName());
        });
        CommonSmsNotification smsNotification = new CommonSmsNotification(contactMap);
        smsNotification.setContent(content);
        messagePublishProvider.publishAsync(NotificationMessageConverter.convertToSmsMessage(smsNotification));
    }

    @Override
    public void notifyMember(MemberNotifyDto dto) {
        // 通过推送短信通知提醒所有成员
        pushingSms(dto.getContactDataList(), dto.getMemberType(),null);
    }
}
