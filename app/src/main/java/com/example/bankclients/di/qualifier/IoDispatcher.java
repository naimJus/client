package com.example.bankclients.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * IO Dispatcher qualifier
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface IoDispatcher {
}

