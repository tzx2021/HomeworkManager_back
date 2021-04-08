package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.UserService;
import sc.hqu.graduationdesign.homeworkmanager.mapper.SystemAccountDao;

/**
 * @author tzx
 * @date 2021-04-07 22:06
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SystemAccountDao systemAccountDao;

    @Override
    public void updatePass(Long account, String newPass) {
        systemAccountDao.updatePasswordByAccount(newPass,account);
    }

    @Override
    public void updatePhoneNum(Long account, String newPhoneNum) {
        systemAccountDao.updateContactByAccount(newPhoneNum,account);
    }
}
