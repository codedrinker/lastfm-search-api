package com.github.codedrinker.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.codedrinker.model.LastfmArtist;
import com.github.codedrinker.model.LastfmResult;

import java.util.List;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmArtistApi extends LastfmApi {
    @Override
    protected String path() {
        return "2.0/";
    }

    @Override
    protected void parse(JSONObject jsonObject, LastfmResult lastfmResult) {
        if (jsonObject == null || jsonObject.getJSONObject("artistmatches") == null) {
            return;
        }
        JSONObject trackmatches = jsonObject.getJSONObject("artistmatches");
        if (trackmatches.containsKey("artist")) {
            String track = trackmatches.getString("artist");
            List<LastfmArtist> artists = JSON.parseArray(track, LastfmArtist.class);
            lastfmResult.setArtists(artists);
        }
    }
}
