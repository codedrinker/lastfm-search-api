package com.github.codedrinker.crawler;

import com.github.codedrinker.config.Configuration;
import com.github.codedrinker.helpers.Loader;
import com.github.codedrinker.model.LastfmTrack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmYoutubeCrawlerTest {
    LastfmCrawler lastfmCrawler;
    Configuration configuration;

    @Before
    public void setUp() throws Exception {
        lastfmCrawler = new LastfmCrawler();
        configuration = new Loader().load();
    }

    @Test
    public void get_not_found() throws Exception {
        LastfmTrack lastfmTrack = lastfmCrawler.fetch("Adele", "Rolling in the Deep", configuration);
        Assert.assertNotNull(lastfmTrack.getYoutubeId());
    }

    @Test
    public void get_found() throws Exception {
        LastfmTrack lastfmTrack = lastfmCrawler.fetch("Adele", "Chasing Pavements", configuration);
        Assert.assertNotNull(lastfmTrack.getYoutubeId());
    }
    @Test
    public void get_found_without_duration() throws Exception {
        LastfmTrack lastfmTrack = lastfmCrawler.fetch("陈一发儿", "童话镇", configuration);
        Assert.assertNotNull(lastfmTrack.getYoutubeId());
        Assert.assertNotNull(lastfmTrack.getDuration());
    }
}
