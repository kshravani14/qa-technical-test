package com.pressassociation.automation.driver;

import org.apache.http.HttpStatus;

public class AppResponse {
    int httpStatus = HttpStatus.SC_OK;
    String jsonBody = "";
    String test;

    AppResponse(int httpStatus, String jsonBody) {
        this.httpStatus = httpStatus;
        this.jsonBody = jsonBody;
    }
}
