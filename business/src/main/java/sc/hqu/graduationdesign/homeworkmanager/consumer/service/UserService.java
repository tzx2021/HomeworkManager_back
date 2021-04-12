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

    /**
     * 验证绑定手机
     * @param account       教工号
     * @param phoneNum      手机号
     * @return              绑定手机正确返回true，否则返回false
     */
    boolean verifyPhone(Long account,String phoneNum);

}
