package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Getter;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 该对象是与Web身份验证请求相关的所选HTTP详细信息的持有者，基于该对象进行认证授权的流程，
 * 就是将一个http请求和自定义的AuthenticationDetails一起封装成一个WebAuthenticationDetails对象
 * @author tzx
 * @date 2021-03-27 15:07
 */
@Getter
public class AuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 目前仅支持教师登录，这里的username就是教师的工号
     */
    private final String username;

    /**
     * 登录密码
     */
    private final String password;

    public AuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.username = obtainUsername(request);
        this.password = obtainPassword(request);
    }

    private String obtainUsername(HttpServletRequest request){
        String username = request.getParameter("username");
        return username != null ? username : "";
    }

    private String obtainPassword(HttpServletRequest request){
        String password = request.getParameter("password");
        return password != null ? password : "";
    }

}
