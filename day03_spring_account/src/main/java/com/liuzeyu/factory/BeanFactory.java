package com.liuzeyu.factory;

import com.liuzeyu.service.IAccountService;
import com.liuzeyu.utils.TransactionManger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by liuzeyu on 2020/4/23.
 * 创建代理service工厂
 *      增强方法：添加事务管理
 */
public class BeanFactory {
    private  IAccountService accountService;
    private TransactionManger txManger;

    public final void setTxManger(TransactionManger txManger) {
        this.txManger = txManger;
    }
    public  void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 获取service的代理对象
     * @return
     */
    public IAccountService getAccountService() {
        IAccountService iac = (IAccountService)Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object rstValue = null;
                        try {
                            //1.开启事务
                            txManger.beginTransaction();
                            //2.执行操作
                            rstValue = method.invoke(accountService, args);
                            //3.提交事务
                            txManger.commitTransaction();
                            //4.返回结果
                            return rstValue;
                        } catch (Exception e) {
                            //5.回滚操作
                            txManger.rollbackTransaction();
                            throw new RuntimeException(e);
                        } finally {
                            //6.释放资源
                            txManger.release();
                        }
                    }
                });
        return iac;
    }

}
