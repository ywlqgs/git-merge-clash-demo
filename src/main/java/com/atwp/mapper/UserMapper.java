package com.atwp.mapper;

import com.atwp.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层接口
 */
public interface UserMapper {

    /**
     * 注册：插入用户的数据
     * @param user 用户的数据
     * @return 收影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户是否存在（t_user表中username设置了unique约束）
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 根据用户的uid修改用户密码
     *          @Param("SQL映射文件中#{}占位符的变量名")
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updatePasswordByUid(Integer uid, @Param("password") String password,
                                String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id查询用户的数据
     * @param uid
     * @return
     */
    User findByUid(Integer uid);

    /**
     * 根据uid修改个人信息
     * @param user
     * @return
     */
    Integer updateInfoByUid(User user);

    /**
     * 上传用户头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(Integer uid,@Param("avater") String avatar,
                               String modifiedUser ,Date modifiedTime );
}
