package sc.hqu.graduationdesign.homeworkmanager.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author tzx
 * @date 2021-03-27 14:24
 */
public class SecurityContextUtil {

    public static UserDetails userDetails(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
