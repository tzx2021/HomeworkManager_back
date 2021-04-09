package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.MemberNotifyDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.NotificationCreateDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.NotificationMemberDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.SimpleFileDataDto;

import java.util.List;

/**
 * 通知服务接口
 * @author tzx
 * @date 2021-04-07 20:26
 */
public interface NotificationService {

    /**
     * 获取通知分页数据
     * @param account       系统账号（教工号）
     * @param pageSize      分页大小
     * @param pageNum       分页索引
     * @return              分页数据
     */
    Object getNotificationPageData(Long account,int pageSize,int pageNum);

    /**
     * 通过通知id获取通知的成员数据
     * @param notificationId    通知id
     * @return                  {@link NotificationMemberDto}
     */
    List<NotificationMemberDto> getMemberDataById(Long notificationId);

    /**
     * 通过通知id获取发布在该通知的所有文件信息
     * @param notificationId    通知id
     * @return                  {@link SimpleFileDataDto}
     */
    List<SimpleFileDataDto> getFileDataById(Long notificationId);

    /**
     * 创建通知
     * @param dto           {@link NotificationCreateDto}
     * @param account       系统账号即教工号
     * @return              返回创建完成的通知数据对象
     */
    NotificationCreateDto create(NotificationCreateDto dto,Long account);

    /**
     * 短信提醒成员
     * @param dto           {@link MemberNotifyDto}
     */
    void notifyMember(MemberNotifyDto dto);

}
