package sc.hqu.graduationdesign.homeworkmanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拒绝或权限不足的请求处理器
 * @author tzx
 * @date 2021-03-27 16:24
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        LOG.error("用户权限不足",e);
    }
}
