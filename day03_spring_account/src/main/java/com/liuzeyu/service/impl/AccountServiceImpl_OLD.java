package com.liuzeyu.service.impl;

import com.liuzeyu.dao.IAccountDao;
import com.liuzeyu.domain.Account;
import com.liuzeyu.service.IAccountService;
import com.liuzeyu.utils.TransactionManger;

import java.util.List;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl_OLD implements IAccountService {

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
        return accountDao.findAccountById(accountId);
    }


    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }


    public void updateAccount(Account account) {

        accountDao.updateAccount(account);
    }


    public void deleteAccount(Integer acccountId) {

        accountDao.deleteAccount(acccountId);
    }

    public void transfer(String sourceName, String targetName, Float money) {

        //2.1根据sourceName获取转出账户
        Account sourceAccount = accountDao.findAccountByName(sourceName);
        //2.2根据targetName获取转入账户
        Account targetAccount = accountDao.findAccountByName(targetName);
        //2.3转出账户数据更新（减钱）
        Float smoney = sourceAccount.getMoney();
        smoney -= money;
        sourceAccount.setMoney(smoney);
        accountDao.updateAccount(sourceAccount);
        //2.4转入账户数据更新（加钱）
        Float tmoney = targetAccount.getMoney();
        tmoney += money;
        targetAccount.setMoney(tmoney);
        //int i = 1 / 0;
        accountDao.updateAccount(targetAccount);
    }
}
