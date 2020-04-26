package com.liuzeyu.test;

import com.liuzeyu.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liuzeyu on 2020/4/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean.xml")
public class AnnotationTest {

    @Autowired
    private IAccountService service;

    @Test
    public void testTransfer(){
        service.transfer("aaa","bbb",100f);
    }
}
