package sc.hqu.graduationdesign.homeworkmanager.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import sc.hqu.graduationdesign.homeworkmanager.constant.ErrorCode;
import sc.hqu.graduationdesign.homeworkmanager.model.AuthenticationUserDetails;
import sc.hqu.graduationdesign.homeworkmanager.model.RequestAuthenticationToken;

import java.util.Collections;
import java.util.Map;

/**
 * @author tzx
 * @date 2021-03-27 13:19
 */
public class RequestAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(RequestAuthenticationProvider.class);

    private final GenericCacheProvider cacheProvider;

    public RequestAuthenticationProvider(GenericCacheProvider cacheProvider){
        this.cacheProvider = cacheProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String requestToken = (String) authentication.getCredentials();
        Object userToken = cacheProvider.get(requestToken);
        UserDetails user;
        if (userToken != null){
            @SuppressWarnings("unchecked")
            Map<String,Object> userInfoMap = (Map<String, Object>) userToken;
            Object username = userInfoMap.get("username");
            if (username == null){
                throw new BadCredentialsException(ErrorCode.INVALID_TOKEN.getDesc());
            }
            user = buildUserDetailsWithUserToken(userInfoMap);
        }else {
            throw new BadCredentialsException(ErrorCode.INVALID_TOKEN.getDesc());
        }
        UsernamePasswordAuthenticationToken authenticatedToken = new UsernamePasswordAuthenticationToken(user,authentication.getCredentials(),user.getAuthorities());
        authenticatedToken.setDetails(authentication.getDetails());
        return authenticatedToken;
    }

    private UserDetails buildUserDetailsWithUserToken(Map<String,Object> userToken){
        return new AuthenticationUserDetails(
                (String) userToken.get("username"),
                "password",
                (String) userToken.get("teacherName"),
                (Integer) userToken.get("gender"),
                (String) userToken.get("contact"),
                (String) userToken.get("address"),
                (String) userToken.get("jobTitle"),
                Collections.emptyList()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RequestAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
