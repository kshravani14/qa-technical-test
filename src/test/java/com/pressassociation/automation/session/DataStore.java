package com.pressassociation.automation.session;

import cucumber.api.Scenario;
import java.util.HashMap;

public class DataStore {

    private static HashMap<Object, Object> data = new HashMap<Object, Object>();

    //session object for scenario which keeps data with key value pair
    public static void setDataKey(String key, Object value){
        data.put(key, value);
    }

    public static Object getDataKey(String key) {
        return data.get(key);
    }
}