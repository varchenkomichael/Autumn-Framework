package com.varchenko.ioc.reflections;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NativeReflectionAgent implements ReflectionAgent {

    Logger logger = Logger.getLogger(NativeReflectionAgent.class);
    private String packageName = "com.varchenko.model";

    public NativeReflectionAgent() {
        super();
    }

    public NativeReflectionAgent(String packageName) {
        this.packageName = packageName;
    }


    @Override
    public Set<Method> getMethodsAnnotationWith(Class<?> type, Class<? extends Annotation> annotation) {
        Set<Method> methods = new HashSet<>();
        if (type != Object.class) {
            for (Method method : type.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
        }
        return methods;
    }

    @Override
    public Set<Class<?>> getClassAnnotatedWith(Class<? extends Annotation> annotation) {
        Set<Class<?>> set = new HashSet<>();
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        assert stream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for (Class<?> annotatedClass : reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet())) {
            if (annotatedClass.isAnnotationPresent(annotation)) {
                set.add(annotatedClass);
            }
        }
        return set;
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            logger.error("Can't find class");
        }
        return null;
    }
}
