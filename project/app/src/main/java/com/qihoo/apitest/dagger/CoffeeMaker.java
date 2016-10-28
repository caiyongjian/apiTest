package com.qihoo.apitest.dagger;

import javax.inject.Inject;

import dagger.Lazy;

class CoffeeMaker {
    private final Lazy<Heater> heater; // Create a possibly costly heater only when we use it.
    private final Pump pump;
    private boolean enableHeater;

    @Inject
    CoffeeMaker(Lazy<Heater> heater, Pump pump, boolean enableHeater) {
        this.heater = heater;
        this.pump = pump;
        this.enableHeater = enableHeater;
    }

    public void brew() {
        if (enableHeater) heater.get().on();
        pump.pump();
        System.out.println(" [_]P coffee! [_]P ");
        if (enableHeater) heater.get().off();
    }
}
