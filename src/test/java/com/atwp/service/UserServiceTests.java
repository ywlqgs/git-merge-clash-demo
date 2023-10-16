package com.atwp.service;

import com.atwp.common.User;
import com.atwp.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

/**
 * 测试用户功能
 */
@SpringBootTest //表示当前的类是一个测试类，不会随同项目一块打包
//@RunWith(SpringRunner.class) 表示启动这个单元测试类（没有此注解单元测试类不能运行）
    // 需要传递一个参数 必须是SpringRunner的实例类型
    //SpringBoot2.2 以后开始没有@RunWith注解。测试类上只需要@SpringBootTest一个注解就可以了
    //原理： spring boot 2.2之前使用的是 Junit4 spring boot 2.2之后使用的是 Junit5
    //文章说明：https://www.jianshu.com/p/bdd22240fe4b
public class UserServiceTests {

    @Autowired
    private IUserService userService; //报错但不影响使用（看p2 1:30h老师的讲解）

    /**
     * 测试注册功能
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("wp3");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK~");
        } catch (ServiceException e) {
           //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    /**
     * 测试MD5加密
     */
    @Test
    public void testMd5(){

        String salt="3342F541-AA14-471C-8517-25B73EF9D924";
        String password="123456";
        //md5加密算法调用(进行三次加密)
        for (int i=0;i<3;i++){
            password= DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
            System.out.println("password"+(i+1)+"="+password);
        }
    }

    /**
     * 测试登录功能
     */
    @Test
    public void login(){
        User user = userService.login("wp1", "123456");
        System.out.println("user="+user);
    }

    //测试更新密码
    @Test
    public void changePassword(){
        userService.changePassword(15,"员工","123","123456");
    }

}
