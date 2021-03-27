package sc.hqu.graduationdesign.homeworkmanager.filter;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.model.RequestAuthenticationToken;
import sc.hqu.graduationdesign.homeworkmanager.utils.ResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tzx
 * @date 2021-03-27 13:50
 */
public class RequestAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestAuthenticationFilter.class);

    /**
     * 请求认证白名单，该名单中的请求不需要进行权限认证
     */
    private final List<RequestMatcher> authWhiteRequestMatchers;

    public RequestAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint,String[] authWhiteList) {
        super(authenticationManager, authenticationEntryPoint);
        this.authWhiteRequestMatchers = antMatchers(authWhiteList);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (this.requiresAuthentication(request)){
            // 与前端进行约定，将token信息放置在请求头的Authorization属性中
            String token = request.getHeader("Authorization");
            if (!StringUtils.hasLength(token)){
                LOG.error("请求token无效，拒绝访问: {}",request.getRequestURI());
                // token为空直接返回400-凭证无效的错误
                ResponseUtil.writeJson(response, ErrorCode.INVALID_TOKEN);
                return;
            }else {
                try {
                    // 封装为请求鉴权认证的token对象，进行后续的认证流程
                    RequestAuthenticationToken requestToken = new RequestAuthenticationToken(token);
                    // 需要封装为spring-security规定的认证接口对象
                    Authentication authentication = this.getAuthenticationManager().authenticate(requestToken);
                    // 保存到上下文对象中，方便在其他地方获取该认证对象
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }catch (AuthenticationException e){
                    this.getAuthenticationEntryPoint().commence(request,response,e);
                    return;
                }
            }
        }
        // 传递过滤链
        chain.doFilter(request,response);
    }

    private boolean requiresAuthentication(HttpServletRequest request){
        // 不在认证白名单的请求才需要进行认证，因此这里返回的是没有匹配命中的结果
        return authWhiteRequestMatchers.stream().anyMatch(requestMatcher -> !requestMatcher.matches(request));
    }

    private List<RequestMatcher> antMatchers(String...antPatterns){
        List<RequestMatcher> matchers = new ArrayList<>();
        // 将请求白名单的url封装为一个RequestMatcher对象
        for (String pattern : antPatterns) {
            matchers.add(new AntPathRequestMatcher(pattern, null));
        }
        return matchers;
    }

}
