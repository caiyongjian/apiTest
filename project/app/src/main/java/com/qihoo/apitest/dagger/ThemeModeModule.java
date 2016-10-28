package com.qihoo.apitest.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by caiyongjian on 16-10-28.
 */

@Module
public class ThemeModeModule {

    @CoffeeScope
    @Provides
    public ThemeMode provideThemeMode(boolean isNight) {
        return new ThemeMode(isNight);
    }

    @Provides
    public boolean provideEnableHeater() {
        return true;
    }
}
