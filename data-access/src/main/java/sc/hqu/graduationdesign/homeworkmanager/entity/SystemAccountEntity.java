package sc.hqu.graduationdesign.homeworkmanager.entity;

import lombok.Data;

/**
 * 系统账户表实体类
 * @author tzx
 * @date 2021-03-14 2:02
 */
@Data
public class SystemAccountEntity {

    /**
     * 系统登录账号，当前教师端为教工号
     */
    private Long account;

    /**
     * 系统登录密码
     */
    private String password;

    /**
     * 账户类型，1表示教师
     * 目前设计2代表学生，3代表家长，用于兼容后续的系统接入或扩展
     */
    private Integer type;

    /**
     * 启用状态，1表示启用，0表示禁用
     * 禁用后将无法登录
     */
    private Integer enable;

}
