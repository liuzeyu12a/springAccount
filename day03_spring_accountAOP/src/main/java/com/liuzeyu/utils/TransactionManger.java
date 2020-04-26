package com.liuzeyu.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Created by liuzeyu on 2020/4/22.
  和事务管理相关的工具类，它包含了事务提交，事务开启，事务回滚
*/
@Component("txManger")
@Aspect
public class TransactionManger {
    @Autowired
    private ConnectionUtils connectionUtils;

    @Pointcut("execution(* com.liuzeyu.service.impl.AccountServiceImpl_OLD.*(..))")
    private void pt1(){}

    //开启事务
    @Before("pt1()")
    public void beginTransaction(){
        System.out.println("前置通知...beginTransaction");
        try {
            connectionUtils.getLocalThreadConnection().setAutoCommit(false);  //返回连接池中
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //提交事务
    @After("pt1()")
    public void commitTransaction(){
        System.out.println("后置通知...commitTransaction");
        try {
            connectionUtils.getLocalThreadConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //回滚事务
    @AfterThrowing("pt1()")
    public void rollbackTransaction(){
        System.out.println("异常通知...rollbackTransaction");
        try {
            //connectionUtils.getLocalThreadConnection().setAutoCommit(false);
            connectionUtils.getLocalThreadConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //释放连接
    @After("pt1()")
    public void release(){
        System.out.println("最终通知...release");
        try {
            connectionUtils.getLocalThreadConnection().close();
            connectionUtils.removeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //环绕通知
   //@Around("pt1()")
    public Object round(ProceedingJoinPoint pjp){
        Object rstValue = null;
        try{
            //1.获取参数
            Object[] args = pjp.getArgs();
            //2.开启事务
            this.beginTransaction();
            System.out.println("前置通知...beginTransaction");
            //3.执行方法
            rstValue = pjp.proceed(args);
            //提交事务
            this.commitTransaction();
            System.out.println("后置通知...commitTransaction");
            return rstValue;

        }catch (Throwable t){
            this.rollbackTransaction();
            System.out.println("异常通知...rollbackTransaction");
            throw new RuntimeException(t);
        }finally {
            this.release();
            System.out.println("最终通知...release");
        }
    }

}
