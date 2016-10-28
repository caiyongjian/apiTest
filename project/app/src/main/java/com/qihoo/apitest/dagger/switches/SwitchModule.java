package com.qihoo.apitest.dagger.switches;

import com.qihoo.apitest.dagger.Switch;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by caiyongjian on 16-10-25.
 */
@Module
public class SwitchModule {
    @Provides
    @Singleton
    Switch provideSwitch() {
        return new OnSwitch();
    }
}
