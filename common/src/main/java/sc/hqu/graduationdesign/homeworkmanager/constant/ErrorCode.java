package sc.hqu.graduationdesign.homeworkmanager.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tzx
 * @date 2021-03-18 0:56
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    /** 系统全局异常错误码*/
    MESSAGE_SENDING_ERROR(510,"短信发送失败");

    private final Integer code;
    private final String desc;

}
