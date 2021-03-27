package sc.hqu.graduationdesign.homeworkmanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证未通过时的记录点对象，通过commence方法处理认证未通过的请求
 * @author tzx
 * @date 2021-03-27 16:15
 */
public class WebAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOG = LoggerFactory.getLogger(WebAuthenticationEntryPoint.class);

    private final GenericCacheProvider cacheProvider;

    public WebAuthenticationEntryPoint(GenericCacheProvider cacheProvider){
        this.cacheProvider = cacheProvider;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        LOG.info("授权认证未通过，当前请求路径为: {}",httpServletRequest.getRequestURI(),e);
        // 对指定类型的认证异常返回对应的响应对象
        if (e instanceof AccountStatusException){
            // 账户禁用异常，需要移除缓存中userToken
            String userToken = httpServletRequest.getHeader("Authorization");
            cacheProvider.remove(userToken);
            ResponseUtil.writeJson(httpServletResponse, GenericResponse.error(ErrorCode.USER_NOT_AVAILABLE));
            return;
        }

        // 其他异常都按照token无效来处理
        ResponseUtil.writeJson(httpServletResponse,GenericResponse.error(ErrorCode.INVALID_TOKEN));

    }

}
