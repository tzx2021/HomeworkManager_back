package sc.hqu.graduationdesign.homeworkmanager.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sc.hqu.graduationdesign.homeworkmanager.constant.AccountStatus;
import sc.hqu.graduationdesign.homeworkmanager.entity.SystemAccountEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.TeacherEntity;
import sc.hqu.graduationdesign.homeworkmanager.mapper.SystemAccountDao;
import sc.hqu.graduationdesign.homeworkmanager.model.AuthenticationUserDetails;

import java.util.Collections;

/**
 * 登录认证的用户信息查询服务
 * @author tzx
 * @date 2021-03-27 12:22
 */
public class LoginAuthenticationServiceImpl implements UserDetailsService {

    private final SystemAccountDao systemAccountDao;

    public LoginAuthenticationServiceImpl(SystemAccountDao systemAccountDao){
        this.systemAccountDao = systemAccountDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long teacherNo = Long.getLong(username);
        SystemAccountEntity systemAccountEntity = systemAccountDao.queryByTeacherNo(teacherNo);
        if (systemAccountEntity == null){
            // 用户不存在
            throw new UsernameNotFoundException("User with account ["+username+"] not found!");
        }
        if (systemAccountEntity.getEnable() == AccountStatus.OFF.ordinal()){
            // 用户被禁用
            throw new DisabledException("User with account ["+username+"] has been disabled!");
        }
        // 查询教师身份信息
        TeacherEntity teacherEntity = systemAccountDao.queryTeacherInfoByTeacherNo(teacherNo);
        return new AuthenticationUserDetails(
                username,
                systemAccountEntity.getPassword(),
                teacherEntity.getName(),
                teacherEntity.getGender() == 1 ? "男" : "女",
                teacherEntity.getContact(),
                teacherEntity.getAddress(),
                teacherEntity.getJobTitle(),
                Collections.emptyList()
        );
    }
}
