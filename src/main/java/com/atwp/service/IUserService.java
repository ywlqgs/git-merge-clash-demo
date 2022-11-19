package com.atwp.service;

import com.atwp.entity.User;

/**
 * 用户模块业务层接口
 */
public interface IUserService {

    /**
     * 用户注册
     * @param user
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return 当前匹配的数据，如果没有返回null
     */
    User login(String username, String password);

    /**
     * 修改密码
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(Integer uid ,String username, String oldPassword ,String newPassword);

    /**
     * 根据用户uid查询用户的数据
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /**
     * 更新用户数据
     * @param uid
     * @param uername
     * @param user
     */
    void changeInfo(Integer uid,String uername,User user);

    /**
     * 上传/修改用户头像
     * @param uid
     * @param avatar
     * @param username
     */
    void changeAvatar(Integer uid,String avatar,String username);
}
