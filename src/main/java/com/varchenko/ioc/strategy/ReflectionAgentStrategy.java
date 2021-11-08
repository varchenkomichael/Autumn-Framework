package com.varchenko.ioc.strategy;

import com.varchenko.ioc.loader.Property;
import com.varchenko.ioc.reflections.NativeReflectionAgent;
import com.varchenko.ioc.reflections.ReflectionAgent;
import com.varchenko.ioc.reflections.ReflectionsApiAgent;


public class ReflectionAgentStrategy implements AgentStrategy {

    private Property property;

    public ReflectionAgentStrategy(Property property) {
        this.property = property;
    }

    @Override
    public ReflectionAgent getReflectionAgent() {
        if (property.getReflections().equals("api")) {
            return new ReflectionsApiAgent();
        } else if (property.getReflections().equals("native")) {
            return new NativeReflectionAgent();
        } else {
            return null;
        }
    }
}