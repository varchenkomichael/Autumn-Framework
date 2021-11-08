package com.varchenko.ioc.loader;

public enum Keys {
    REFLECTIONS("reflections"),
    MISHA("sdfadsfads"),
    CAR("bmw");

    private final String properties;

    Keys(String properties) {
        this.properties = properties;
    }

    public String getProperties() {
        return properties;
    }
}
