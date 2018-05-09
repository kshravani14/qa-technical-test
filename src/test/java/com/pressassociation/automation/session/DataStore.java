package com.pressassociation.automation.session;

import cucumber.api.Scenario;
import java.util.HashMap;

public class DataStore {
    private static Scenario currentScenario = null;
    private static HashMap<Object, Object> data = new HashMap<Object, Object>();

    public static Scenario getCurrentScenario() {
        return currentScenario;
    }

    public static void setCurrentScenario(Scenario currentScenario) {
        DataStore.currentScenario = currentScenario;
    }

    public static void setDataKey(String key, Object value){
        data.put(key, value);
    }

    public static Object getDataKey(String key) {
        return data.get(key);
    }
}