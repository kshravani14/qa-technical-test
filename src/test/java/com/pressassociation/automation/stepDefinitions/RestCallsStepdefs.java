package com.pressassociation.automation.stepDefinitions;

import com.pressassociation.automation.driver.RestCallDriver;
import com.pressassociation.automation.json.SongJsonBody;
import com.pressassociation.automation.session.DataStore;
import com.pressassociation.automation.utils.ConfigLoader;
import com.pressassociation.automation.utils.RawObjectToJsonConvertor;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.util.List;

import static com.pressassociation.automation.constants.FrameworkConstants.*;


public class RestCallsStepdefs {
    
    @Before
    public void loadConfig(){
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.loadConfig();
    }

    @Then("^the response code is (\\d+)$")
    public void theResponseCodeIs(int arg0) throws Throwable {
        Assert.assertEquals(arg0, DataStore.getDataKey(RESPONSECODE));
    }

}
