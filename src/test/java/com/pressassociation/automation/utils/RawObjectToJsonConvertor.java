package com.pressassociation.automation.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RawObjectToJsonConvertor {

    public static JSONObject rawToJson(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject)parser.parse(response);
    }

    public static JSONArray rawToJsonArray(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONArray)parser.parse(response);
    }

}
