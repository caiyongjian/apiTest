package com.qihoo.apitest.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by caiyongjian on 16-10-28.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CoffeeScope {
}
