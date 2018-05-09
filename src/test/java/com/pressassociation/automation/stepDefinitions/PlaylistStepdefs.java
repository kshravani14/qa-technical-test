package com.pressassociation.automation.stepDefinitions;

import com.pressassociation.automation.driver.RestCallDriver;
import com.pressassociation.automation.session.DataStore;
import com.pressassociation.automation.utils.RawObjectToJsonConvertor;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.util.List;

import static com.pressassociation.automation.constants.FrameworkConstants.*;

public class PlaylistStepdefs {

    RestCallDriver restCallDriver = new RestCallDriver();
    JSONObject responseJSONObject;
    JSONObject outerRequestObject;
    JSONObject innerRequestObject;
    JSONArray jsonArray;

    @When("^I create a '(.*?)' with data as follows$")
    public void iCreateAPlaylistWithDataAsFollows(String resourceName, DataTable playListDetails) throws Throwable {
        List<List<String>> data = playListDetails.raw();
        for (int i = 1; i < data.size(); i++) {
            outerRequestObject = new JSONObject();
            outerRequestObject.put(data.get(0).get(0), data.get(i).get(0));
            outerRequestObject.put(data.get(0).get(1), data.get(i).get(1));

            restCallDriver.callPostRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                    DataStore.getDataKey(resourceName + "Resource"), outerRequestObject.toJSONString());
        }
        responseJSONObject = RawObjectToJsonConvertor.rawToJson(DataStore.getDataKey(RESPONSEBODY).toString());
        if (resourceName.equals("Video")) {
            updateSessionFor(SONGID);
        } else {
            updateSessionFor(PLAYLISTID);
        }
    }

    public void updateSessionFor(String sessionKey) {
        if (DataStore.getDataKey(sessionKey) != null) {
            DataStore.setDataKey(sessionKey, DataStore.getDataKey(sessionKey) + "," + responseJSONObject.get("_id"));
        } else {
            DataStore.setDataKey(sessionKey, responseJSONObject.get("_id"));
        }
    }

    @And("^the response body contains 'key' to be 'value'$")
    public void theResponseBodyContainsKeyToBeValue(DataTable playlistResponse) throws Throwable {
        List<List<String>> data = playlistResponse.raw();
        responseJSONObject = RawObjectToJsonConvertor.rawToJson(DataStore.getDataKey(RESPONSEBODY).toString());
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).get(0).contains("videos")) {
                System.out.println(responseJSONObject.get(data.get(i).get(0)));
                Assert.assertEquals(data.get(i).get(1), ((JSONArray)responseJSONObject.get(data.get(i).get(0))).size()+"");
            } else if (data.get(i).get(1).equals("'NOT_NULL'")) {
                Assert.assertNotNull(responseJSONObject.get(data.get(i).get(0)).toString());
            } else {
                Assert.assertEquals(data.get(i).get(1), responseJSONObject.get(data.get(i).get(0)).toString());
            }
        }
    }

    @When("^I (.*?) created video (to|from) playlist$")
    public void iAddCreatedVideoToPlaylist(String methodOperation, String temp) throws Throwable {
        createAndMakePatchPlayListcallWithOperation(DataStore.getDataKey(PLAYLISTID).toString(),
                DataStore.getDataKey(SONGID).toString(), methodOperation);
    }

    @When("^I get all creted playlists$")
    public void iGetAllCretedPlaylists() throws Throwable {
        restCallDriver.callGetRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                DataStore.getDataKey("PlaylistResource"));
    }

    @And("^the response body contains all created playlists$")
    public void theResponseBodyContainsAllCreatedPlaylists() throws Throwable {
        jsonArray = RawObjectToJsonConvertor.rawToJsonArray(DataStore.getDataKey(RESPONSEBODY).toString());

        String[] expectedPlaylistID = DataStore.getDataKey(PLAYLISTID).toString().split(",");
        for (int i = 0; i < expectedPlaylistID.length; i++) {
            Assert.assertTrue(jsonArray.toString().contains(expectedPlaylistID[i]));
        }
    }

    @After("@CleanPlayList")
    public void cleanPlayList() throws Throwable {
        jsonArray = RawObjectToJsonConvertor.rawToJsonArray(DataStore.getDataKey(RESPONSEBODY).toString());
        for (int i = 0; i < jsonArray.size(); i++) {
            restCallDriver.callDeleteRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                    DataStore.getDataKey("PlaylistResource") + "/" + ((JSONObject) (jsonArray.get(i))).get("_id"));
        }
    }

    @After
    public void cleanDataStore() {
        DataStore.setDataKey(PLAYLISTID, null);
        DataStore.setDataKey(SONGID, null);
    }

    @When("^I get a created playlist$")
    public void iGetACreatedPlaylist() throws Throwable {
        restCallDriver.callGetRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                DataStore.getDataKey("PlaylistResource") + "/" + DataStore.getDataKey(PLAYLISTID));
    }

    @When("^I try to get a non existing playlist with (.*?)$")
    public void iTryToGetANonExistingPlaylistWithID(String playListID) throws Throwable {
        restCallDriver.callGetRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                DataStore.getDataKey("PlaylistResource") + "/" + playListID);
    }

    @When("^I add created video to a non existing playlist with (.*?)$")
    public void iAddCreatedVideoToANonExistingPlaylistWithID(String playListId) throws Throwable {
        createAndMakePatchPlayListcall(playListId, DataStore.getDataKey(SONGID).toString());
    }

    @When("^I add invalid video (.*?) to playlist$")
    public void iAddInvalidVideoIDToPlaylist(String videoId) throws Throwable {
        createAndMakePatchPlayListcall(DataStore.getDataKey(PLAYLISTID).toString(), videoId);
    }

    public void createAndMakePatchPlayListcall(String playListId, String videoID) throws Throwable {
        createAndMakePatchPlayListcallWithOperation(playListId, videoID, null);
    }

    public void createAndMakePatchPlayListcallWithOperation(String playListId, String videoID, String method) throws Throwable {
        innerRequestObject = new JSONObject();
        outerRequestObject = new JSONObject();
        jsonArray = new JSONArray();

        if(method == null)
            method = "add";
        innerRequestObject.put(videoID, method);
        jsonArray.add(0, innerRequestObject);
        outerRequestObject.put("videos", jsonArray);

        restCallDriver.callPatchRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                DataStore.getDataKey("PlaylistResource") + "/" + playListId, outerRequestObject.toJSONString());
    }

    @When("^I delete created playlist$")
    public void iDeleteCreatedPlaylist() throws Throwable {
        restCallDriver.callDeleteRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                DataStore.getDataKey("PlaylistResource") + "/" + DataStore.getDataKey(PLAYLISTID));
    }

    @When("^I delete non existing playlist with (.*?)$")
    public void iDeleteNonExistingPlaylistWithId(String playlistId) throws Throwable {
        restCallDriver.callDeleteRestEndPoint(DataStore.getDataKey("baseURL").toString() +
                DataStore.getDataKey("PlaylistResource") + "/" + playlistId);
    }

    @When("^I delete the video$")
    public void iDeleteTheVideo() throws Throwable {
        restCallDriver.callDeleteRestEndPoint(DataStore.getDataKey("baseURL").toString()+
                DataStore.getDataKey("VideoResource")+"/"+DataStore.getDataKey(SONGID));
        Assert.assertEquals(DataStore.getDataKey(RESPONSECODE), 204);
    }
}
