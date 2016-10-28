package com.qihoo.apitest.dagger.pump;

import com.qihoo.apitest.dagger.Pump;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PumpModule {
  @Binds
  abstract Pump providePump(Thermosiphon pump);
}
