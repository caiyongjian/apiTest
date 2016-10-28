package com.qihoo.apitest.dagger;

import com.qihoo.apitest.dagger.pump.PumpModule;
import com.qihoo.apitest.dagger.heater.DripCoffeeModule;
import com.qihoo.apitest.dagger.switches.SwitchModule;

import javax.inject.Singleton;

import dagger.Component;

public class CoffeeApp {
  @Singleton
  @Component(modules = {
          DripCoffeeModule.class,
          PumpModule.class,
          SwitchModule.class,

  })

  public interface Coffee {
    CoffeeMaker maker();
  }

  public static void main(String[] args) {
      Coffee coffee = DaggerCoffeeApp_Coffee.builder().build();
    coffee.maker().setEnableHeater(false);
      coffee.maker().brew();
  }

}
