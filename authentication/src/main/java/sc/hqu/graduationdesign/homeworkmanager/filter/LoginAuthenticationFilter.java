package sc.hqu.graduationdesign.homeworkmanager.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sc.hqu.graduationdesign.homeworkmanager.config.WebSecurityConfig;
import sc.hqu.graduationdesign.homeworkmanager.model.LoginAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tzx
 * @date 2021-03-27 13:21
 */
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 仅支持post类型的表单登录认证
     */
    private static final String POST_KEY = "POST";

    public LoginAuthenticationFilter() {
        super(new AntPathRequestMatcher(WebSecurityConfig.DEFAULT_LOGIN_URL, POST_KEY));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals(POST_KEY)){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }else {
            String username = request.getParameter("username");
            String password= request.getParameter("password");
            if (password == null){
                password = "";
            }
            if (username != null){
                username = username.trim();
                password = password.trim();
                // 封装用于认证的token对象
                LoginAuthenticationToken loginToken = new LoginAuthenticationToken(username,password);
                this.setDetails(request,loginToken);
                // 交由认证管理对象去进行后续的认证流程
                return this.getAuthenticationManager().authenticate(loginToken);
            }else {
                throw new AuthenticationServiceException("Login username must not be null!");
            }
        }
    }

    protected void setDetails(HttpServletRequest request, LoginAuthenticationToken token){
        // 通过authenticationDetailsSource对象去为token对象创建一个details对象
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
