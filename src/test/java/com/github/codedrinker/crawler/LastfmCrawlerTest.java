package com.github.codedrinker.crawler;

import com.github.codedrinker.model.LastfmCompositeResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmCrawlerTest {
    LastfmCrawler lastfmCrawler;

    @Before
    public void setUp() throws Exception {
        lastfmCrawler = new LastfmCrawler();
    }

    @Test
    public void search() throws Exception {
        LastfmCompositeResult lastfmResult = lastfmCrawler.search("Hello");
        Assert.assertNotNull(lastfmResult.getAlbum());
        Assert.assertTrue(StringUtils.containsIgnoreCase(lastfmResult.getAlbum().getAlbums().get(0).getName(), "hello"));
        Assert.assertNotNull(lastfmResult.getArtist());
        Assert.assertTrue(StringUtils.containsIgnoreCase(lastfmResult.getArtist().getArtists().get(0).getName(), "hello"));
        Assert.assertNotNull(lastfmResult.getTrack());
        Assert.assertTrue(StringUtils.containsIgnoreCase(lastfmResult.getTrack().getTracks().get(0).getName(), "hello"));
    }
}
