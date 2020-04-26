package com.liuzeyu.service.impl;

import com.liuzeyu.dao.IAccountDao;
import com.liuzeyu.domain.Account;
import com.liuzeyu.service.IAccountService;
import com.liuzeyu.utils.TransactionManger;

import java.util.List;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService{

    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private TransactionManger txManger;

    public void setTxManger(TransactionManger txManger) {
        this.txManger = txManger;
    }

    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }


    public Account findAccountById(Integer accountId) {
        try {
            //1.开启事务
            txManger.beginTransaction();
            //2.执行操作
            Account account = accountDao.findAccountById(accountId);
            //3.提交事务
            txManger.commitTransaction();
            //4.返回结果
            return account;
        }catch (Exception e){
            //5.回滚操作
            txManger.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            //6.释放资源
            txManger.release();
        }
    }


    public void saveAccount(Account account) {
        try {
            //1.开启事务
            txManger.beginTransaction();
            //2.执行操作
           accountDao.saveAccount(account);
            //3.提交事务
            txManger.commitTransaction();
        }catch (Exception e){
            //5.回滚操作
            txManger.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            //6.释放资源
            txManger.release();
        }
    }


    public void updateAccount(Account account) {
        try {
            //1.开启事务
            txManger.beginTransaction();
            //2.执行操作
            accountDao.updateAccount(account);
            //3.提交事务
            txManger.commitTransaction();
        }catch (Exception e){
            //4.回滚操作
            txManger.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            //5.释放资源
            txManger.release();
        }
    }


    public void deleteAccount(Integer acccountId) {
        try {
            //1.开启事务
            txManger.beginTransaction();
            //2.执行操作
            accountDao.deleteAccount(acccountId);
            //3.提交事务
            txManger.commitTransaction();
        }catch (Exception e){
            //4.回滚操作
            txManger.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            //5.释放资源
            txManger.release();
        }

    }

    public void transfer(String sourceName, String targetName, Float money) {

        try {
            //1.开启事务
            txManger.beginTransaction();
            //2.执行操作
            //2.1根据sourceName获取转出账户
            Account sourceAccount = accountDao.findAccountByName(sourceName);
            //2.2根据targetName获取转入账户
            Account targetAccount = accountDao.findAccountByName(targetName);
            //2.3转出账户数据更新（减钱）
            Float smoney = sourceAccount.getMoney();
            smoney -=money;
            sourceAccount.setMoney(smoney);
            accountDao.updateAccount(sourceAccount);
            //2.4转入账户数据更新（加钱）
            Float tmoney = targetAccount.getMoney();
            tmoney +=money;
            targetAccount.setMoney(tmoney);
            int i = 1/0;
            accountDao.updateAccount(targetAccount);
            //3.提交事务
            txManger.commitTransaction();
        }catch (Exception e){
            //4.回滚操作

            txManger.rollbackTransaction();
            e.printStackTrace();
        }finally {
            //5.释放资源
            txManger.release();
        }
    }
}
