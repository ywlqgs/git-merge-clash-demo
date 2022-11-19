package com.atwp;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.atwp.mapper")
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class,args);
    }

    /**配置图片上传大小和请求大小*/
    @Bean
    public MultipartConfigElement multipartConfigElement(){

        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        //设置图片最大上传为10MB!
        multipartConfigFactory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
        //设置请求大小为15MB
        multipartConfigFactory.setMaxRequestSize(DataSize.of(15,DataUnit.MEGABYTES));

        return multipartConfigFactory.createMultipartConfig();

    }
}
