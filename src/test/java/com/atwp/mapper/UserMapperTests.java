package com.atwp.mapper;

import com.atwp.common.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * 测试用户功能
 */
@SpringBootTest //表示当前的类是一个测试类，不会随同项目一块打包
//@RunWith(SpringRunner.class) 表示启动这个单元测试类（没有此注解单元测试类不能运行）
    // 需要传递一个参数 必须是SpringRunner的实例类型
    //SpringBoot2.2 以后开始没有@RunWith注解。测试类上只需要@SpringBootTest一个注解就可以了
    //原理： spring boot 2.2之前使用的是 Junit4 spring boot 2.2之后使用的是 Junit5
    //文章说明：https://www.jianshu.com/p/bdd22240fe4b
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper; //报错但不影响使用（看p2 1:30h老师的讲解）

    /**
     * 单元测试方法：满足以下四个条件就可以独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须用@Test注解
     * 2.返回值类型必须为void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUserName(){
        String username="admin";

        User user = userMapper.findByUserName(username);
        System.out.println("user="+user);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(14,"123456","管理员",new Date());
    };

    @Test
    public void findByUid(){
        System.out.println("user="+userMapper.findByUid(14));
    };

}
