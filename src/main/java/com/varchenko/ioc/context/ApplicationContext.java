package com.varchenko.ioc.context;

public interface ApplicationContext {
    <T> T getBean(Class<T> tClass);
    void searchAnnotatedByComponentSingletonClass();
}
