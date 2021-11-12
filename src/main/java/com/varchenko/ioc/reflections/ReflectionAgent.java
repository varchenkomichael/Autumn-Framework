package com.varchenko.ioc.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ReflectionAgent {
    Set<Class<?>> getClassAnnotatedWith(Class<? extends Annotation> annotation);

    Set<Method> getMethodsAnnotationWith(Class<?> type, Class<? extends Annotation> annotation);
}
