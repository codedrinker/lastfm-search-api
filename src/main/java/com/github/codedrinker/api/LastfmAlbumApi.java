
package com.github.codedrinker.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.codedrinker.model.LastfmAlbum;
import com.github.codedrinker.model.LastfmResult;

import java.util.List;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmAlbumApi extends LastfmApi {
    @Override
    protected String path() {
        return "2.0/";
    }

    @Override
    protected void parse(JSONObject jsonObject, LastfmResult lastfmResult) {
        if (jsonObject == null || jsonObject.getJSONObject("albummatches") == null) {
            return;
        }
        JSONObject trackmatches = jsonObject.getJSONObject("albummatches");
        if (trackmatches.containsKey("album")) {
            String album = trackmatches.getString("album");
            List<LastfmAlbum> albums = JSON.parseArray(album, LastfmAlbum.class);
            lastfmResult.setAlbums(albums);
        }
    }
}
