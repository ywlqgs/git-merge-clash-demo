package com.atwp.controller;

import com.atwp.common.JsonResult;
import com.atwp.controller.ex.*;
import com.atwp.entity.User;
import com.atwp.service.IUserService;
import com.atwp.service.ex.InsertException;
import com.atwp.service.ex.UserNameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @PostMapping("reg")
    /**public JsonResult<Void> reg(User user){

        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功~");
        } catch (UserNameDuplicatedException e) { //用户名被占用异常
            result.setState(4000);
            result.setMessage("用户名被占用！");
        } catch (InsertException e) { //服务器被占用异常
            result.setState(5000);
            result.setMessage("注册时产生未知的异常！");
        }

        return result;
    }*/
    public JsonResult<Void> reg(User user){  //使用异常捕获 当有异常产生 自动跳转到BaseController中
        userService.reg(user);
        return new JsonResult<>(OK); //子类继承父类 父类的属性和方法也被继承
    }

    @PostMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username, password);

        //向session对象中完成数据的绑定（session对象是全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        return new JsonResult<>(OK,data);
    }

    @PostMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUserNameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);

        return  new JsonResult<>(OK);
    }

    //修改信息的数据回显
    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){

        Integer uid = getUidFromSession(session);
        User data = userService.getByUid(uid);

        return new JsonResult<>(OK,data);
    }


    @PostMapping("change_info")
    public JsonResult<Void> changeInfo(HttpSession session,User user){

        Integer uid = getUidFromSession(session);
        String username = getUserNameFromSession(session);
        userService.changeInfo(uid,username,user);

        return new JsonResult<>(OK);
    }

    /**设置上传文件的最大值 10MB
     * SpringMVC默认为1MB 可以在配置文件中修改或者在主类中通过@Bean修改*/
    public static final int AVATAR_MAX_SIZE=10*1024*1024;

    /**限制上传文件的类型*/
    public static final List<String> AVATAR_TYPE=new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");//jpeg包含jpg
        AVATAR_TYPE.add("image/png");
       // AVATAR_TYPE.add("image/jpg");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file){

        //判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空！");
        }
        if (file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件大小超过10MB!");
        }
        //判断文件类型是否符合
        String contentType = file.getContentType();
        //contains（）:并传递一个元素时，会执行遍历，逐个对比item是否等于该元素，
        // 当遍历结束后，如果还没有一个元素等于该元素的值，则返回false, 否则返回true
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持！");
        }

        //将文件统一放到upload目录下
        String parent=session.getServletContext().getRealPath("/upload");
        System.out.println(parent);
        File dir = new File(parent);
        if (!dir.exists()){ //如果该目录不存在 则创建
            dir.mkdir();
        }

        //获取文件名称，使用UUID生成一个字符串作为文件名（不改文件名可能会造成文件名（相同）被覆盖）
        //file.getOriginalFilename()是得到上传时的文件名。包括文件后缀
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename="+originalFilename); //测试该方法
        //分割文件
        int index = originalFilename.lastIndexOf(".");
        System.out.println("index="+index);
        String suffix = originalFilename.substring(index);
        System.out.println("suffix="+suffix);
        String fileName = UUID.randomUUID().toString().toUpperCase()+suffix;

        File dest = new File(parent,fileName);  //创建一个空文件（名字为UUID生成的）
        try {
            //将传来的文件传输到空文件中（相当于复制）
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常！");
        }
        catch (IOException e) {
            throw new FileUploadIOException("文件读写异常！");
        }

        Integer uid = getUidFromSession(session);
        String username = getUserNameFromSession(session);
        //返回头像的路径（相对路径）
        String avatar="/upload/"+fileName;
        userService.changeAvatar(uid,avatar,username);

        //返回用户头像的路径给前端页面 用于头像的展示
        return new JsonResult<>(OK,avatar);
    }
}
