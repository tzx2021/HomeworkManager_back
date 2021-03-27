package sc.hqu.graduationdesign.homeworkmanager.model;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过该对象去为每个web请求都构建一个用于进行认证的{@link AuthenticationDetails}对象
 * @author tzx
 * @date 2021-03-27 12:53
 */
public class WebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest,AuthenticationDetails> {

    @Override
    public AuthenticationDetails buildDetails(HttpServletRequest request) {
        return new AuthenticationDetails(request);
    }

}
