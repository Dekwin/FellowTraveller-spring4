package com.fellowtraveler.exceptions;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Created by igorkasyanenko on 19.03.17.
 */
public class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("Method Name::"+method.getName());
        System.out.println("Exception occurred::"+ ex);
    }
}