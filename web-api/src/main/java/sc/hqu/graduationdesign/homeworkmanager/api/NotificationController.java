package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.constant.NotificationType;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.*;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.NotificationService;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.*;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.GenericPageDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.SimpleFileOutput;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tzx
 * @date 2021-04-07 2:16
 */
@Api(tags = "通知服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/notification")
public class NotificationController {


    @Autowired
    private NotificationService notificationService;

    @ApiOperation(value = "获取通知分页数据")
    @PostMapping(value = "/page")
    public Object getNotificationPage(@RequestBody PageQueryInput input){
        System.out.println(input);
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        return notificationService.getNotificationPageData(account, input.getPageSize(), input.getPageNum());
    }

    @ApiOperation(value = "获取通知的成员信息")
    @PostMapping(value = "/members")
    public List<NotificationMemberDto> getNotifyMembers(@RequestBody NotificationIdInput input){
        return notificationService.getMemberDataById(input.getId());
    }

    @ApiOperation(value = "获取附件通知的附件信息")
    @PostMapping(value = "/files")
    public List<SimpleFileDataDto> getNotificationFiles(@RequestBody NotificationIdInput input){
        return notificationService.getFileDataById(input.getId());
    }

    @ApiOperation(value = "发布通知")
    @PostMapping(value = "/publish")
    public GenericResponse publishNotification(@RequestBody CreateNotificationInput input){
        System.out.println(input);
        NotificationCreateDto createDto = new NotificationCreateDto();
        BeanUtils.copyProperties(input,createDto);
        if (input.isConfirmable()){
            createDto.setConfirmable(1);
        }else {
            createDto.setConfirmable(0);
        }

        // 封装联系人数据
        List<NotificationCreateDto.SimpleContactData> contactDataList = new ArrayList<>();
        // 发布对象的pid统一封装在SimpleContactData中
        List<NotificationCreateDto.SimpleContactData> simpleContactData = input.getContactDataList();
        simpleContactData.forEach(scd -> {
            NotificationCreateDto.SimpleContactData contactData = new NotificationCreateDto.SimpleContactData();
            BeanUtils.copyProperties(scd,contactData);
            contactDataList.add(contactData);
        });

        // 封装附件数据
        List<FilePublishDto> attachments = input.getAttachments();

        createDto.setContactDataList(contactDataList);
        createDto.setAttachments(attachments);
        Long account = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        NotificationOutputDto dto = notificationService.create(createDto, account);
        return GenericResponse.successWithData(dto);
    }

    @ApiOperation(value = "短信提醒成员")
    @PostMapping(value = "/notify")
    public GenericResponse notifyMembers(@RequestBody NotifyMembersInput input){
        System.out.println(input);
        // 短信提醒只需要成员的身份、姓名和练习方式
        MemberNotifyDto dto = new MemberNotifyDto();
        dto.setMemberType(input.getMemberType());
        dto.setNotificationId(input.getNotificationId());
        List<NotificationCreateDto.SimpleContactData> contactDataList = new ArrayList<>();
        input.getNotifyInfos().forEach(notifyInfo -> {
            NotificationCreateDto.SimpleContactData contactData = new NotificationCreateDto.SimpleContactData();
            BeanUtils.copyProperties(notifyInfo,contactData);
            contactDataList.add(contactData);
        });
        dto.setContactDataList(contactDataList);
        notificationService.notifyMember(dto);
        return GenericResponse.success();
    }

}
