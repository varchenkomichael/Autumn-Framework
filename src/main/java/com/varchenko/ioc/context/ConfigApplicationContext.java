package com.varchenko.ioc.context;

import com.varchenko.ioc.annotations.Bean;
import com.varchenko.ioc.annotations.Component;
import com.varchenko.ioc.annotations.Configuration;
import com.varchenko.ioc.strategy.AgentStrategy;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ConfigApplicationContext implements ApplicationContext {

    private final Logger logger = Logger.getLogger(ConfigApplicationContext.class);
    private final Map<Class<?>, Object> container = new HashMap<>();
    private final AgentStrategy agentStrategy;

    public ConfigApplicationContext(AgentStrategy agentStrategy) {
        this.agentStrategy = agentStrategy;
        searchAnnotatedByComponentSingletonClass();
        processConfigurationBeans();
        System.out.println("Container: " + container);
    }

    @Override
    public <T> T getBean(Class<T> className) {
        return className.cast(container.get(className));
    }

    private Object createBean(Class<?> beanType) throws InstantiationException, IllegalAccessException {
        return beanType.newInstance();
    }

    public void searchAnnotatedByComponentSingletonClass() {
        Set<Class<?>> set = agentStrategy.getReflectionAgent().getClassAnnotatedWith(Component.class);
        for (Class<?> classType : set) {
            fillContainer(classType);
        }
    }

    private void fillContainer(Class<?> classType) {
        try {
            Object instance = createBean(classType);
            container.put(classType, instance);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Can't create instance of class.");
        }
    }

    public void processConfigurationBeans() {
        Set<Class<?>> set = null;
        try {
            set = agentStrategy.getReflectionAgent().getClassAnnotatedWith(Configuration.class);
            for (Class<?> classType : set) {
                Object bean = createBean(classType);

                Set<Method> methods = agentStrategy.getReflectionAgent().getMethodsAnnotationWith(bean.getClass(), Bean.class);
                for (Method method : methods) {
                    Object returningValueOfAnnotatedMethod = method.invoke(bean, (Object[]) null);
                    container.put(returningValueOfAnnotatedMethod.getClass(), returningValueOfAnnotatedMethod);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Can't process/init beans defined in the configurations: " + set + ".");
        }
    }
}

