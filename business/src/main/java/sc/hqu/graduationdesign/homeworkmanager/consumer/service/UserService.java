package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

/**
 * 用户服务接口
 * @author tzx
 * @date 2021-04-07 20:26
 */
public interface UserService {

    /**
     * 更新密码
     * @param account       账号
     * @param newPass       新的密码
     */
    void updatePass(Long account,String newPass);

    /**
     * 更新手机号
     * @param account       账号
     * @param newPhoneNum   新的手机号
     */
    void updatePhoneNum(Long account,String newPhoneNum);

}
