package com.qihoo.apitest.dagger.switches;

import com.qihoo.apitest.dagger.Switch;

/**
 * Created by caiyongjian on 16-10-25.
 */

public class OnSwitch implements Switch {
    @Override
    public boolean on() {
        return true;
    }

    @Override
    public boolean off() {
        return false;
    }
}
