package com.qihoo.apitest.dagger.pump;

import com.qihoo.apitest.dagger.Heater;
import com.qihoo.apitest.dagger.Pump;

import javax.inject.Inject;

class Thermosiphon implements Pump {
  private final Heater heater;

  @Inject
  Thermosiphon(Heater heater) {
    this.heater = heater;
  }

  @Override public void pump() {
    if (heater.isHot()) {
      System.out.println("=> => pumping => =>");
    }
  }
}
