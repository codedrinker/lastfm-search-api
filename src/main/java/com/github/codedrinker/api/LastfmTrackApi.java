package com.github.codedrinker.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.codedrinker.model.LastfmResult;
import com.github.codedrinker.model.LastfmTrack;

import java.util.List;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmTrackApi extends LastfmApi {
    @Override
    protected String path() {
        return "2.0/";
    }

    @Override
    protected void parse(JSONObject jsonObject, LastfmResult lastfmResult) {
        if (jsonObject == null || jsonObject.getJSONObject("trackmatches") == null) {
            return;
        }
        JSONObject trackmatches = jsonObject.getJSONObject("trackmatches");
        if (trackmatches.containsKey("track")) {
            String track = trackmatches.getString("track");
            List<LastfmTrack> tracks = JSON.parseArray(track, LastfmTrack.class);
            lastfmResult.setTracks(tracks);
        }
    }
}
