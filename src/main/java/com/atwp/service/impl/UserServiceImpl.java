package com.atwp.service.impl;

import com.atwp.common.User;
import com.atwp.mapper.UserMapper;
import com.atwp.service.IUserService;
import com.atwp.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层的实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {

        String username = user.getUsername();

        User result = userMapper.findByUserName(username);
        if (result!=null){ //抛出异常
            throw new UserNameDuplicatedException("用户名已被占用！");
        }

        //密码加密 串+密码+串 md5加密（串也成为盐值）
        String oldPassword = user.getPassword();
        //获取一个盐值（随机生成一个盐值）
        String salt = UUID.randomUUID().toString().toUpperCase();
        //将密码和盐值作为一个整体进行加密
        String md5Password = getMD5Password(oldPassword, salt);

        //补全必要数据
        user.setSalt(salt); //补全盐值
        user.setPassword(md5Password);
        Date date = new Date();
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        user.setCreatedTime(date);
        user.setModifiedTime(date);


        //开始注册
        Integer rows = userMapper.insert(user);
        if (rows!=1){ //没有注册成功时
            throw new InsertException("用户在注册时产生了未知异常！");
        }


    }

    @Override
    public User login(String username, String password) {

        //检测用户是否存在
        User result = userMapper.findByUserName(username);
        if (result==null){
            throw new UserNotFoundException("用户不存在！");
        }

        //检测密码是否正确
        String salt = result.getSalt();
        String userPassword = result.getPassword();
        String md5Password = getMD5Password(password, salt);
        if (!md5Password.equals(userPassword)){
            throw new PasswordNotMatchException("密码不正确！");
        }

        //判断is_delete字段是否为1 为1抛出异常
        if (result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在！");
        }

        //用户脱敏（只要uid username avatar三个字段）
        User safetyUser = new User();
        safetyUser.setUid(result.getUid());
        safetyUser.setUsername(result.getUsername());
        safetyUser.setAvatar(result.getAvatar());

        return safetyUser;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {

        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在!");
        }
        //原始密码和数据库密码对比
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码输入错误！");
        }

        //将新的密码加密设置到数据库中
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新密码时产生未知的异常！");
        }
    }

    @Override
    public User getByUid(Integer uid) {

        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在！");
        }

        //用户脱敏 只返回前端所要的数据
        User safetyUser = new User();
        safetyUser.setUsername(result.getUsername());
        safetyUser.setPhone(result.getPhone());
        safetyUser.setEmail(result.getEmail());
        safetyUser.setGender(result.getGender());

        return safetyUser;
    }

    @Override
    public void changeInfo(Integer uid,String username,User user) {

        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在！");
        }

        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1){
            throw new UpdateException("修改个人信息时产生未知的更新异常！");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前的用户是否存在
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户数据不存在！无法修改头像!");
        }

        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新用户头像时产生未知的异常！");
        }

    }


    /**
     * MD5算法的加密处理
     */
    private String getMD5Password(String password ,String salt){

        //md5加密算法调用(进行三次加密)
        for (int i=0;i<3;i++){
            password=DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }

        //返回加密之后的密码
        return password;
    }
}
