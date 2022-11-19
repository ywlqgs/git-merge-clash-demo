package com.atwp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component //将当前类的对象创建使用维护交由spring容器维护
@Aspect //将当前类标记为切面类
public class TimerAspect {

    @Around("execution(* com.atwp.service.impl.*.*(..))")
    private Object around(ProceedingJoinPoint pjp) throws Throwable {

        //调用方法前记录执行时间
        long start = System.currentTimeMillis();
        System.out.println("start="+start);

        Object result = pjp.proceed(); //调用目标方法

        //调用方法后记录执行时间
        long end = System.currentTimeMillis();
        System.out.println("end="+end);
        System.out.println("此方法耗时："+(end-start)+"毫秒");

        return result;
    }
}
