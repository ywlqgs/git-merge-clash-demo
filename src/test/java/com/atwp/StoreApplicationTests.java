package com.atwp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest(classes = StoreApplicationTests.class)
class StoreApplicationTests {

    @Test
    void contextLoads() {
    }

    //@Autowired
    @Resource
    private DataSource dataSource;


    @Test
    void getConnection() throws SQLException {
        //HikariProxyConnection@10873709 wrapping com.mysql.cj.jdbc.ConnectionImpl@307087
        System.out.println(dataSource.getConnection());
    }

}
