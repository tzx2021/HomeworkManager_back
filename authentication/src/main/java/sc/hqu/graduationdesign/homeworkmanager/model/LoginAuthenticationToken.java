package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 用户登录认证时的鉴权令牌对象，该对象用于封装认证前后的认证字段的数据
 * 用户通过登录认证时，该对象会持有用户在系统中的被设置的访问权限，默认是全局权限
 * @author tzx
 * @date 2021-03-27 12:56
 */
@Getter
public class LoginAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * spring-security进行认证时的主要对象，对应了用户登录时的username
     */
    private final Object principal;

    /**
     * spring-security进行认证时的证书对象，对应了用户登录的password
     */
    private Object credentials;

    public LoginAuthenticationToken(Object principal,Object credentials){
        super(null);
        this.credentials = credentials;
        this.principal = principal;
        this.setAuthenticated(false);
    }

    public LoginAuthenticationToken(Object credentials,Object principal,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = credentials;
        this.principal = principal;
        this.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated){
            throw new IllegalArgumentException("It's forbidden to set set this token to trusted, please using constructor which takes a GrantedAuthority list instead");
        }else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
