package com.qihoo.apitest.dagger.heater;

import com.qihoo.apitest.dagger.Heater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module/*(includes = PumpModule.class)*/
public class DripCoffeeModule {
  @Provides @Singleton
  Heater provideHeater() {
    return new ElectricHeater();
  }
}
