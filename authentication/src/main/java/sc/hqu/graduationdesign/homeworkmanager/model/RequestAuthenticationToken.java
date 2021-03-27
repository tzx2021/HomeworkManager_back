package sc.hqu.graduationdesign.homeworkmanager.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * 用户请求访问的鉴权令牌对象，所有web请求每次进入到服务端时都需要进行接口鉴权认证
 * 在通过认证后才可以进行最终的接口访问。每个请求都会持有自己的认证令牌对象
 * @author tzx
 * @date 2021-03-27 13:05
 */
public class RequestAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 接口鉴权的token数据
     */
    private final Object credentials;

    public RequestAuthenticationToken(Object credentials){
        super(null);
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        // 接口鉴权只有token数据，不需要principal
        return null;
    }
}
