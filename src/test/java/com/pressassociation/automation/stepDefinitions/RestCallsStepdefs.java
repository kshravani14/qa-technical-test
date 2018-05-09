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
    RestCallDriver restCallDriver = new RestCallDriver();
    SongJsonBody songJsonBody = new SongJsonBody();
    JSONObject responseJSONObject;
    JSONArray responseJSONArray;
    
    
    @Before
    public void loadConfig(){
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.loadConfig();
    }

    @Before("@clerdata")
    public void cleanupData() throws Throwable {
        iTryDoAVideoResourceGETCallToFetchAllAboveCreatedSong("VideoResource");
        responseJSONArray = RawObjectToJsonConvertor.rawToJsonArray(DataStore.getDataKey(RESPONSEBODY).toString());
        for(int i=0; i<responseJSONArray.size(); i++) {
            DataStore.setDataKey(SONGID, ((JSONObject)(responseJSONArray.get(i))).get("_id"));
            iDeleteTheCreatedRecordUsingEndpointWithDELETEMethod("VideoResource");
        }
        DataStore.setDataKey(SONGID, null);
    }
    
    @Given("^I create default song record using '(.*?)' endpoint with POST method$")
    public void iCreateDefaultSongRecordWithPOSTEndpoint(String endpointName) throws Throwable {
        restCallDriver.callPostRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                DataStore.getDataKey(endpointName), songJsonBody.getJsonSongBody());
        Assert.assertEquals(DataStore.getDataKey(RESPONSECODE), 201);

        responseJSONObject = RawObjectToJsonConvertor.rawToJson(DataStore.getDataKey(RESPONSEBODY).toString());
        DataStore.setDataKey(SONGID, responseJSONObject.get("_id"));
        
    }

    @When("^I try do a '(.*?)' GET by id call to fetch above created song$")
    public void iTryDoAGETCallToFetechAboveCreatedSong(String endpointName) throws Throwable {
        restCallDriver.callGetRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                DataStore.getDataKey(endpointName)+"/"+DataStore.getDataKey(SONGID));
    }

    @Then("^the response code is (\\d+)$")
    public void theResponseCodeIs(int arg0) throws Throwable {
        Assert.assertEquals(arg0, DataStore.getDataKey(RESPONSECODE));
    }

    @And("^the response body has '(.*?)' to be '(.*?)'$")
    public void theResponseBodyHasDefaultValue(String dataField, String expectedValue) throws Throwable {
        responseJSONObject = RawObjectToJsonConvertor.rawToJson(DataStore.getDataKey(RESPONSEBODY).toString());
        Assert.assertEquals(expectedValue, responseJSONObject.get(dataField).toString());
    }

    @And("^the response body has '(.*?)' is 'NOT_NULL'$")
    public void theResponseBodyHasDefault_idIsNOT_NULL(String fieldName) throws Throwable {
        Assert.assertNotNull(responseJSONObject.get(fieldName));
    }

    @And("^the response body has default song id$")
    public void theResponseBodyHasDefaultSongId() throws Throwable {
        Assert.assertEquals(DataStore.getDataKey(SONGID), responseJSONObject.get("_id"));
    }

    @And("^I delete the created record using '(.*?)' endpoint with DELETE method$")
    public void iDeleteTheCreatedRecordUsingEndpointWithDELETEMethod(String endpointName) throws Throwable {
        restCallDriver.callDeleteRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                DataStore.getDataKey(endpointName)+"/"+DataStore.getDataKey(SONGID));
        Assert.assertEquals(DataStore.getDataKey(RESPONSECODE), 204);
    }

    @Given("^I create songs records for below details using '(.*?)' endpoint with POST method$")
    public void iCreateSongsRecordsForBelowDetailsUsingVideoResourceEndpointWithPOSTMethod
            (String endpointName, DataTable songDetails) throws Throwable {
        List<List<String>> data = songDetails.raw();
        DataStore.setDataKey(SONGID, null);
        for (int i=0; i<data.size(); i++) {
            songJsonBody = new SongJsonBody(data.get(i).get(0), data.get(i).get(1));
            restCallDriver.callPostRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                    DataStore.getDataKey(endpointName), songJsonBody.getJsonSongBody());
            Assert.assertEquals(DataStore.getDataKey(RESPONSECODE), 201);
            responseJSONObject = RawObjectToJsonConvertor.rawToJson(DataStore.getDataKey(RESPONSEBODY).toString());
            if(DataStore.getDataKey(SONGID) != null) {
                DataStore.setDataKey(SONGID, DataStore.getDataKey(SONGID)+","+responseJSONObject.get("_id"));
            } else {
                DataStore.setDataKey(SONGID, responseJSONObject.get("_id"));
            }
        }
    }

    @When("^I try do a '(.*?)' GET call to fetch all above created song$")
    public void iTryDoAVideoResourceGETCallToFetchAllAboveCreatedSong(String endpointName) throws Throwable {
        restCallDriver.callGetRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                DataStore.getDataKey(endpointName));
    }

    @And("^the response body has all the songs created$")
    public void theResponseBodyHasAllTheSongsCreated() throws Throwable {
        responseJSONArray = RawObjectToJsonConvertor.rawToJsonArray(DataStore.getDataKey(RESPONSEBODY).toString());
        Assert.assertEquals(3, responseJSONArray.size());
    }

    @And("^I delete  all the created record using '(.*?)' endpoint with DELETE method$")
    public void iDeleteAllTheCreatedRecordUsingVideoResourceEndpointWithDELETEMethod(String endpointName) throws Throwable {
        String songID = DataStore.getDataKey(SONGID).toString();
        String[] songsID = songID.split(",");
        for (int i=0; i< songsID.length; i++) {
            DataStore.setDataKey(SONGID, songsID[i]);
            iDeleteTheCreatedRecordUsingEndpointWithDELETEMethod(endpointName);
        }
    }

    @When("^I try do a '(.*?)' PATCH call to fetch all above created song$")
    public void iTryDoAVideoResourcePATCHCallToFetchAllAboveCreatedSong(String endpointName) throws Throwable {
        restCallDriver.callPatchRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                DataStore.getDataKey(endpointName)+"/"+DataStore.getDataKey(SONGID), "");
    }

    @And("^the response body has message '(.*?)'$")
    public void theResponseBodyHasNotImplemented(String expectedMessage) throws Throwable {
        Assert.assertTrue(DataStore.getDataKey(RESPONSEBODY).toString().contains(expectedMessage));
    }

    @Given("^I create json body with below details for '(.*?)' endpoint with POST method$")
    public void iCreateJsonBodyWithBelowDetailsForVideoResourceEndpointWithPOSTMethod
            (String endpointName, DataTable songDetails) throws Throwable {
        List<List<String>> data = songDetails.raw();
        DataStore.setDataKey(SONGID, null);
        String jsonRuestBody = "{";
        for (int i=0; i<data.size(); i++) {
            if( i>0 )
                jsonRuestBody += ",";
            jsonRuestBody += "\""+data.get(i).get(0)+"\":";
            jsonRuestBody += "\""+data.get(i).get(1)+"\"";
        }
        jsonRuestBody += "}";
        restCallDriver.callPostRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                DataStore.getDataKey(endpointName), jsonRuestBody);
    }
}
