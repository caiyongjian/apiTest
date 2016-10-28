package com.qihoo.apitest.dagger;

public class CoffeeApp {
  public static void main(String[] args) {
      Coffee coffee = DaggerCoffee.builder().build();
      CoffeeMaker coffeeMaker = coffee.maker();
      coffeeMaker.brew();
  }

}
