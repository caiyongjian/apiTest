package com.qihoo.apitest.dagger;

import com.qihoo.apitest.dagger.heater.DripCoffeeModule;
import com.qihoo.apitest.dagger.pump.PumpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by caiyongjian on 16-10-28.
 */

@CoffeeScope
@Singleton
@Component(modules = {DripCoffeeModule.class, PumpModule.class, ThemeModeModule.class})

public abstract class Coffee {
    abstract void inject(DaggerActivity activity);
    abstract void inject(Dagger2Activity dagger2Activity);


    abstract CoffeeMaker maker();

    private static Coffee sCoffee;
    public static Coffee getInstance() {
        if (sCoffee == null) {
            sCoffee = DaggerCoffee.builder().build();
        }
        return sCoffee;
    }
}
