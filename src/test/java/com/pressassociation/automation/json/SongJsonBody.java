package com.pressassociation.automation.json;

import gherkin.deps.com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.pressassociation.automation.constants.FrameworkConstants.DEFAULT_SINGER;
import static com.pressassociation.automation.constants.FrameworkConstants.DEFAULT_SONG;

public class SongJsonBody {

    String artistName = DEFAULT_SINGER;
    String songName = DEFAULT_SONG;

    JSONObject songJsonObject = new JSONObject();

    public SongJsonBody() {

    }

    public SongJsonBody(String artistName, String songName) {
        this.artistName = artistName;
        this.songName = songName;
    }

    public String getJsonSongBody() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        songJsonObject.put("artist", artistName);
        songJsonObject.put("song", songName);
        songJsonObject.put("publishDate", simpleDateFormat.format(new Date()));

        return songJsonObject.toJSONString();
    }

}
