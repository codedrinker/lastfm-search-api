package com.github.codedrinker.api;

import com.github.codedrinker.dto.LastfmQuery;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastQueryStringBuilderTest {
    @Test
    public void build() throws Exception {
        String baseUrl = "ws.audioscrobbler.com";
        String path = "2.0/";
        LastfmQuery lastfmQuery = new LastfmQuery();
        lastfmQuery.setApi_key("key");
        lastfmQuery.setTrack("Believe");
        lastfmQuery.setFormat("json");
        lastfmQuery.setMethod("track.search");
        String endpoint = LastfmQueryStringBuilder.build(baseUrl, path, lastfmQuery);
        Assert.assertEquals("http://ws.audioscrobbler.com/2.0/?api_key=key&track=Believe&method=track.search&format=json", endpoint);

    }
}
