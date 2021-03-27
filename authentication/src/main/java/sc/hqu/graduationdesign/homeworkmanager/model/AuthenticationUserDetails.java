package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

/**
 * 该对象的作用和目的是通过登录请求去获取请求里的登录所需要认证的数据，用于后续的认证
 * 该对象是用于用户自身的认证逻辑的数据封装，例如数据库查询用户信息
 * @author tzx
 * @date 2021-03-27 12:44
 */
@Getter
public class AuthenticationUserDetails extends User {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 目前仅支持教师登录，这里的username就是教师的工号
     */
    private final String username;

    /**
     * 登录密码
     */
    private final String password;

    private final String teacherName;

    private final String gender;

    private final String contact;

    private final String address;

    private final String jobTitle;

    public AuthenticationUserDetails(String username,String password,Collection<? extends GrantedAuthority> authorities) {
        this(username,password,"","","","","",authorities);
    }

    public AuthenticationUserDetails(String username, String password, String teacherName, String gender, String contact, String address, String jobTitle, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.teacherName = teacherName;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
        this.jobTitle = jobTitle;
    }

}
