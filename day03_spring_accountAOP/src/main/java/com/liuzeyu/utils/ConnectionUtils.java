package com.liuzeyu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by liuzeyu on 2020/4/22.
 *  * 工具类：作用是用于从数据库连接池获取一个连接并和当前线程绑定
 */
@Component("connectUtils")
public class ConnectionUtils {
    private ThreadLocal<Connection> t1 = new ThreadLocal<Connection>();

    @Autowired
    private DataSource dataSource;


    /**
     * 获取当前线程上的连接
     */
    public Connection getLocalThreadConnection(){
        try{
            //1.先从ThreadLocal上获取
            Connection conn = t1.get();
            //2.判断当前线程是否有连接
            if( conn == null){
                //3.从数据源获取一个连接，并存入ThreadLocal中
                conn = dataSource.getConnection();
                t1.set(conn);
            }
            //4.返回当前连接
            return conn;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 处理连接已经返回到连接池中了，但是线程还是和连接绑定在一起
     */
    public void removeConnection(){
        t1.remove();  //线程和连接绑定
    }
}
