package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.UserService;
import sc.hqu.graduationdesign.homeworkmanager.entity.SystemAccountEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.TeacherEntity;
import sc.hqu.graduationdesign.homeworkmanager.mapper.SystemAccountDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.TeacherDao;

/**
 * @author tzx
 * @date 2021-04-07 22:06
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SystemAccountDao systemAccountDao;

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public void updatePass(Long account, String newPass) {
        System.out.println(account);
        System.out.println(newPass);
        systemAccountDao.updatePasswordByAccount(newPass,account);
    }

    @Override
    public void updatePhoneNum(Long account, String newPhoneNum) {
        systemAccountDao.updateContactByAccount(newPhoneNum,account);
    }

    @Override
    public boolean verifyPhone(Long account, String phoneNum) {
        TeacherEntity teacherEntity = teacherDao.queryByTeacherNo(account);
        if (teacherEntity != null){
            return teacherEntity.getContact().equals(phoneNum);
        }
        return false;
    }
}
