package sc.hqu.graduationdesign.homeworkmanager.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sc.hqu.graduationdesign.homeworkmanager.model.AuthenticationUserDetails;
import sc.hqu.graduationdesign.homeworkmanager.model.LoginAuthenticationToken;

/**
 * @author tzx
 * @date 2021-03-27 13:16
 */
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;

    public LoginAuthenticationProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginAuthenticationToken loginToken = (LoginAuthenticationToken) authentication;
        String username = (String) loginToken.getPrincipal();
        String password = (String) loginToken.getCredentials();
        UserDetails userDetails;
        try {
            userDetails = retrieveUser(username);
            if (!userDetails.getPassword().equals(password)){
                throw new BadCredentialsException("登录密码错误");
            }
        }catch (UsernameNotFoundException e){
            LOG.error("用户[{}]未找到",username,e);
            throw e;
        }catch (DisabledException e){
            LOG.error("用户[{}]已被禁用",username,e);
            throw e;
        }
        // 封装为通过认证的token对象
        LoginAuthenticationToken authenticatedLoginToken = new LoginAuthenticationToken(userDetails,authentication.getCredentials(),userDetails.getAuthorities());
        authenticatedLoginToken.setDetails(userDetails);
        return authenticatedLoginToken;
    }

    private UserDetails retrieveUser(String username){
        try {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (user == null){
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            }
            return user;
        }catch (UsernameNotFoundException | DisabledException | InternalAuthenticationServiceException e1){
            throw e1;
        }catch (Exception e2){
            throw new InternalAuthenticationServiceException(e2.getMessage(),e2);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 配置可以进行认证处理的token
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
