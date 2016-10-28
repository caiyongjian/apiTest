package com.qihoo.apitest;

import com.qfix.testlib.used.Calc;

/**
 * Created by caiyongjian on 16-9-6.
 */
public class NormalTest {
    public static void Test() {
        Assert.setEnable(true);
        Assert.assertEquals(new Calc().add(6, 5), 11);
    }
}
