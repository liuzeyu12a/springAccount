package com.liuzeyu.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by liuzeyu on 2020/4/25.
 */
@Configuration
@ComponentScan(basePackages = "com.liuzeyu")
@EnableAspectJAutoProxy
public class SpringConfig {

    @Bean(name = "runner")
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }

    @Bean(name ="dataSource")
    public DataSource getDataSource(){
        try {
            ComboPooledDataSource source = new ComboPooledDataSource();
            source.setDriverClass("com.mysql.jdbc.Driver");
            source.setJdbcUrl("jdbc:mysql://localhost:3306/eesy");
            source.setUser("root");
            source.setPassword("809080");

            return source;
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
}
