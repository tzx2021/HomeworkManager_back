package sc.hqu.graduationdesign.homeworkmanager.config;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sc.hqu.graduationdesign.homeworkmanager.filter.LoginAuthenticationFilter;
import sc.hqu.graduationdesign.homeworkmanager.filter.RequestAuthenticationFilter;
import sc.hqu.graduationdesign.homeworkmanager.handler.*;
import sc.hqu.graduationdesign.homeworkmanager.mapper.SystemAccountDao;
import sc.hqu.graduationdesign.homeworkmanager.model.WebAuthenticationDetailsSource;
import sc.hqu.graduationdesign.homeworkmanager.provider.GenericCacheProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.LoginAuthenticationProvider;
import sc.hqu.graduationdesign.homeworkmanager.provider.RequestAuthenticationProvider;
import sc.hqu.graduationdesign.homeworkmanager.service.LoginAuthenticationServiceImpl;

import java.util.Arrays;

/**
 * spring-security主配置类，用于登录鉴权以及接口鉴权的相关配置
 * @author tzx
 * @date 2021-03-27 12:06
 */
@Configuration
@ConfigurationProperties("homeworkmanager.security")
@ComponentScan(basePackages = "sc.hqu.graduationdesign.homeworkmanager")
@Setter
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 目前仅支持用户名名及密码登录这一种登录方式，暂不考虑其他登录方式*/
    public static final String DEFAULT_LOGIN_URL = "/api/homeworkmanager/login";

    public static final String DEFAULT_LOGOUT_URL = "/api/homeworkmanager/logout";

    private final String[] defaultWebIgnoring = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/doc.html",
            "/v2/api-docs",
            "/webjars/**",
            "/images/**",
            "/css/**",
            "/js/**",
            // 文件上传的路径必须要开放
            "/api/homeworkmanager/file/upload",
            // 短信接口也需要开放
            "/api/homeworkmanager/sms/**",
            "/api/homeworkmanager/user/update/pass"
    };

    private final String[] defaultAuthWhiteList = {
            DEFAULT_LOGIN_URL,
            "/test/**"
    };

    /**
     * 过滤鉴权的url集合，可通过配置文件进行配置
     */
    private String[] webIgnoringUrls;

    /**
     * 鉴权白名单，会在进行真实鉴权之前进行跳过，最终效果与webIgnoringUrls的效果一致
     */
    private String[] authWhiteList;

    public WebSecurityConfig(){
        // 实例化时先进行默认设置，如果在配置文件中读取到了用户自定义的配置
        // 会在spring进行依赖注入时通过setter方法进行覆盖
        this.webIgnoringUrls = defaultWebIgnoring;
        this.authWhiteList = defaultAuthWhiteList;
    }

    GenericCacheProvider cacheProvider(){
        return this.getApplicationContext().getBean(GenericCacheProvider.class);
    }

    @Bean
    LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager());
        loginAuthenticationFilter.setAuthenticationDetailsSource(authenticationDetailsSource());
        loginAuthenticationFilter.setAuthenticationSuccessHandler(authSuccessHandler());
        loginAuthenticationFilter.setAuthenticationFailureHandler(authFailureHandler());
        return loginAuthenticationFilter;
    }

    @Bean
    LoginAuthenticationProvider loginAuthenticationProvider(){
        return new LoginAuthenticationProvider(loginAuthenticationService());
    }

    @Bean
    RequestAuthenticationProvider requestAuthenticationProvider(){
        return new RequestAuthenticationProvider(cacheProvider());
    }

    @Bean
    LoginAuthenticationServiceImpl loginAuthenticationService(){
        SystemAccountDao accountDao = this.getApplicationContext().getBean(SystemAccountDao.class);
        return new LoginAuthenticationServiceImpl(accountDao);
    }

    @Bean
    WebAuthenticationEntryPoint authenticationEntryPoint(){
        return new WebAuthenticationEntryPoint(cacheProvider());
    }

    @Bean
    WebAuthenticationDetailsSource authenticationDetailsSource(){
        return new WebAuthenticationDetailsSource();
    }

    @Bean
    LoginAuthSuccessHandler authSuccessHandler(){
        return new LoginAuthSuccessHandler(cacheProvider());
    }

    LogoutAuthSuccessHandler logoutAuthSuccessHandler(){
        return new LogoutAuthSuccessHandler(cacheProvider());
    }

    @Bean
    AuthFailureHandler authFailureHandler(){
        return new AuthFailureHandler();
    }

    @Bean
    AuthAccessDeniedHandler authAccessDeniedHandler(){
        return new AuthAccessDeniedHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 对当前系统的请求认证处理的相关配置
        auth
                .userDetailsService(loginAuthenticationService())
                .and()
                // 登录请求的认证处理器
                .authenticationProvider(loginAuthenticationProvider())
                // 接口请求的认证处理器
                .authenticationProvider(requestAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 对全局http请求的认证处理配置
        http
                /*
                 * session管理的配置，这里使用token验证，不需要创建session
                 * STATELESS:
                 *   Spring Security永远不会创建{@link HttpSession}，并且永远不会使用它来获取{@link SecurityContext}
                 * */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //禁用csrf
                .cors().and().csrf().disable()
                .logout()
                .logoutUrl(DEFAULT_LOGOUT_URL)
                //配置自定义的登出成功的handler
                .logoutSuccessHandler(logoutAuthSuccessHandler())
                .and()
                //访问的请求需要进行授权访问
                .authorizeRequests()
                //对访问白名单的请求放行
                .antMatchers(authWhiteList).permitAll()
                .anyRequest().authenticated()
                .and()
                // 登录认证的过滤器
                .addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 请求认证的过滤器
                // 这里的请求认证过滤器一定要手动new出来。不可以加到spring容器中（即不能注册为bean）
                // 否则会出现配置web.ignoring无效的情况
                .addFilter(new RequestAuthenticationFilter(authenticationManager(),authenticationEntryPoint(),authWhiteList))
                .exceptionHandling()
                // 认证失败的记录点
                .authenticationEntryPoint(authenticationEntryPoint())
                // 权限拒绝处理器
                .accessDeniedHandler(authAccessDeniedHandler());
    }

    /**
     * 配置无视鉴权的公共路径
     * @param web           {@link WebSecurity}
     * @throws Exception    配置异常
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(webIgnoringUrls);
    }

}
