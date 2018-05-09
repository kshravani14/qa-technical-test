package com.pressassociation.automation.driver;

import com.pressassociation.automation.session.DataStore;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import static com.pressassociation.automation.constants.FrameworkConstants.RESPONSEBODY;
import static com.pressassociation.automation.constants.FrameworkConstants.RESPONSECODE;

import java.io.IOException;

public class RestCallDriver {

    public void callGetRestEndPoint(String endpointURL) throws IOException{
        AppResponse appResponse = Request.Get(endpointURL).addHeader("Content-Type","application/json").
                execute().handleResponse(new AppResponseHandler());
        DataStore.setDataKey(RESPONSECODE, appResponse.httpStatus);
        DataStore.setDataKey(RESPONSEBODY, appResponse.jsonBody);
    }

    public void callDeleteRestEndPoint(String endpointURL) throws IOException{
        AppResponse appResponse = Request.Delete(endpointURL).addHeader("Content-Type","application/json").
                execute().handleResponse(new AppResponseHandler());
        DataStore.setDataKey(RESPONSECODE, appResponse.httpStatus);
    }

    public void callPostRestEndPoint(String endpointURL, String requestBody) throws IOException{
        AppResponse appResponse = Request.Post(endpointURL).addHeader("Content-Type","application/json").
                bodyString(requestBody, ContentType.APPLICATION_JSON).execute().handleResponse(new AppResponseHandler());
        DataStore.setDataKey(RESPONSECODE, appResponse.httpStatus);
        DataStore.setDataKey(RESPONSEBODY, appResponse.jsonBody);
    }

    public void callPatchRestEndPoint(String endpointURL, String requestBody) throws IOException{
        AppResponse appResponse = Request.Patch(endpointURL).addHeader("Content-Type","application/json").
                bodyString(requestBody, ContentType.APPLICATION_JSON).execute().handleResponse(new AppResponseHandler());
        DataStore.setDataKey(RESPONSECODE, appResponse.httpStatus);
        DataStore.setDataKey(RESPONSEBODY, appResponse.jsonBody);
    }
}
