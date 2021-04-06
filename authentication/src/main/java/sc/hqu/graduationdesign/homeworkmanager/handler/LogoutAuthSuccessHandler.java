package sc.hqu.graduationdesign.homeworkmanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 登出认证成功的处理器
 * @author tzx
 * @date 2021-03-27 16:32
 */
public class LogoutAuthSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutAuthSuccessHandler.class);

    private final GenericCacheProvider cacheProvider;

    public LogoutAuthSuccessHandler(GenericCacheProvider cacheProvider){
        this.cacheProvider = cacheProvider;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String userToken = httpServletRequest.getHeader("Authorization");
        if (userToken != null){
            @SuppressWarnings("unchecked")
            Map<String, Object> info = (Map<String, Object>) cacheProvider.get(userToken);
            if (info != null){
                LOG.info("用户[{}]已成功退出登录",info.get("username"));
                // 移除用户的登录凭证
                cacheProvider.remove(userToken);
            }
            ResponseUtil.writeJson(httpServletResponse, GenericResponse.success());
        }else{
            // TODO: 2021/4/5 用户退出登录的请求应该需要进入RequestAuthenticationFilter中进行前置过滤，但是这里会直接进入到当前handler中
            LOG.info("用户退出登录失败，请求携带的token无效");
            ResponseUtil.writeJson(httpServletResponse, GenericResponse.error(ErrorCode.INVALID_TOKEN));
        }
    }
}
