package sc.hqu.graduationdesign.homeworkmanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tzx
 * @date 2021-03-27 16:27
 */
public class AuthFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        LOG.info("用户登录失败");
        if (e instanceof UsernameNotFoundException){
            ResponseUtil.writeJson(httpServletResponse, ErrorCode.USER_NOT_FOUND);
        }else if (e instanceof AccountStatusException){
            ResponseUtil.writeJson(httpServletResponse,ErrorCode.USER_NOT_AVAILABLE);
        }else if (e instanceof BadCredentialsException){
            ResponseUtil.writeJson(httpServletResponse,ErrorCode.WRONG_PASS);
        }else{
            ResponseUtil.writeJson(httpServletResponse,ErrorCode.LOGIN_SERVICE_ERROR);
        }
    }
}
