package com.varchenko.ioc.reflections;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ReflectionsApiAgent implements ReflectionAgent {
    private final Reflections reflections;

    public ReflectionsApiAgent(Object...namePackage) {
        reflections = new Reflections(namePackage);
    }

    @Override
    public Set<Class<?>> getClassAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }

    @Override
    public Set<Method> getMethodsAnnotationWith(Class<?> type, Class<? extends Annotation> annotation) {
        Set<Method> methods = new HashSet<>();
        Class<?> nonObjectClass = type;
        while (nonObjectClass != Object.class) {
            for (Method method : nonObjectClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)){
                    methods.add(method);
                }
            }
           nonObjectClass = nonObjectClass.getSuperclass();
        }
        return methods;
    }
}