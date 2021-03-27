package sc.hqu.graduationdesign.homeworkmanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sc.hqu.graduationdesign.homeworkmanager.constant.GlobalParam;
import sc.hqu.graduationdesign.homeworkmanager.model.AuthenticationUserDetails;
import sc.hqu.graduationdesign.homeworkmanager.model.GenericResponse;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.utils.JwtUtil;
import sc.hqu.graduationdesign.homeworkmanager.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录认证成功的处理器
 * @author tzx
 * @date 2021-03-27 15:21
 */
public class LoginAuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginAuthSuccessHandler.class);

    private final GenericCacheProvider cacheProvider;

    public LoginAuthSuccessHandler(GenericCacheProvider cacheProvider){
        this.cacheProvider = cacheProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AuthenticationUserDetails userDetails = (AuthenticationUserDetails) authentication.getDetails();
        LOG.info("用户[{}]登录认证成功",userDetails.getUsername());
        Map<String,Object> userInfoMap = new HashMap<>(16);
        userInfoMap.put("username",userDetails.getUsername());
        userInfoMap.put("teacherName",userDetails.getTeacherName());
        userInfoMap.put("gender",userDetails.getGender());
        userInfoMap.put("contact",userDetails.getContact());
        userInfoMap.put("address",userDetails.getAddress());
        userInfoMap.put("jobTitle",userDetails.getJobTitle());
        String userToken = JwtUtil.encode(userDetails.getUsername(), GlobalParam.USER_TOKEN_EXPIRED_TIME * 1000,userInfoMap);
        // 将用户token信息保存到redis缓存中
        cacheProvider.set(userToken,userInfoMap,GlobalParam.USER_TOKEN_EXPIRED_TIME, TimeUnit.SECONDS);
        // 返回响应对象
        ResponseUtil.writeJson(httpServletResponse, GenericResponse.success());
    }



}
