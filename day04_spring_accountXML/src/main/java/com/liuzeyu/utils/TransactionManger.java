package com.liuzeyu.utils;

import java.sql.SQLException;

/**
 * Created by liuzeyu on 2020/4/22.
  和事务管理相关的工具类，它包含了事务提交，事务开启，事务回滚
*/
public class TransactionManger {
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    //开启事务
    public void beginTransaction(){
        System.out.println("前置通知...beginTransaction");
        try {
            connectionUtils.getLocalThreadConnection().setAutoCommit(false);  //返回连接池中
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //提交事务
    public void commitTransaction(){
        System.out.println("后置通知...commitTransaction");
        try {
            connectionUtils.getLocalThreadConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //回滚事务
    public void rollbackTransaction(){
        System.out.println("异常通知...rollbackTransaction");
        try {
            connectionUtils.getLocalThreadConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //释放连接
    public void release(){
        System.out.println("最终通知...release");
        try {
            connectionUtils.getLocalThreadConnection().close();
            connectionUtils.removeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
