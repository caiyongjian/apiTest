package com.qihoo.apitest.dagger;

import javax.inject.Inject;

/**
 * Created by caiyongjian on 16-10-28.
 */

public class ThemeMode {
    protected boolean isNight;

    @Inject
    public ThemeMode() {
        isNight = true;
    }

    public ThemeMode(boolean isNight) {
        this.isNight = isNight;
    }

    boolean isNight() {
        return isNight;
    }
}
