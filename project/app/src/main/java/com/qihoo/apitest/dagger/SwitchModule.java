package com.qihoo.apitest.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by caiyongjian on 16-10-28.
 */

@Module
public class SwitchModule {
    @Provides
    public boolean provideEnableHeater() {
        return true;
    }
}
