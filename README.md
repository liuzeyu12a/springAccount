## springAccount spring银行转账案例

## 案例 day03_spring_account 采用手动创建动态代理机制添加事务

## 案例 day03_spring_accountAOP 采用spring自带的动态代理的AOP的纯注解配置添加事务控制

## 案例 day04_spring_accountXML 采用springspring自带的动态代理的的AOP的XML配置添加事务控制

## 说明
1. 其中XML配置无论从配置环绕通过还是其它四种通知（前置，后置，异常，最终），均可以无死角的通过控制住事务达到增强方法的作用

2. 纯注解配置时，使用四种通知（前置，后置，异常，最终）
如果没有人为手动制造异常，事务将被控制。但是如果是手动创造异常，会导致通知的执行顺序为：前置，后置，最终，异常。将无法达到控制事务增强方法的功能。

纯注解配置时，使用环绕通知，可以无死角的通过控制住事务达到增强方法的作用
所以当我们采用纯注解配置时，应该采用配置环绕通知的方式
