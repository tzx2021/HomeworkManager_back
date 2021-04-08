package sc.hqu.graduationdesign.homeworkmanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.CreateNotificationInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.NotificationDataOutput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.NotifyMembersInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.input.PageQueryInput;
import sc.hqu.graduationdesign.homeworkmanager.vo.output.GenericPageDataOutput;

/**
 * @author tzx
 * @date 2021-04-07 2:16
 */
@Api(tags = "通知服务")
@RestController
@RequestMapping(value = "/api/homeworkmanager/notification")
public class NotificationController {


    @ApiOperation(value = "获取通知分页数据")
    @PostMapping(value = "/page")
    public GenericPageDataOutput<NotificationDataOutput> getNotificationPage(@RequestBody PageQueryInput input){
        return null;
    }

    @ApiOperation(value = "发布通知")
    @PostMapping(value = "/publish")
    public GenericResponse publishNotification(@RequestBody CreateNotificationInput input){
        return GenericResponse.success();
    }

    @ApiOperation(value = "短信提醒成员")
    @PostMapping(value = "/notify")
    public GenericResponse notifyMembers(@RequestBody NotifyMembersInput input){
        return GenericResponse.success();
    }

}
