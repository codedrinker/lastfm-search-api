package com.github.codedrinker.crawler;

import com.github.codedrinker.auth.YoutubeAuthorization;
import com.github.codedrinker.model.LastfmTrack;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmYoutubeCrawlerTest {
    LastfmCrawler lastfmCrawler;
    YoutubeAuthorization youtubeAuthorization;

    @Before
    public void setUp() throws Exception {
        lastfmCrawler = new LastfmCrawler();
        youtubeAuthorization = new YoutubeAuthorization();
        youtubeAuthorization.setApiKey("Youtube-Key");
        youtubeAuthorization.setName("LastFM");
    }

    @Test
    public void get_not_found() throws Exception {
        LastfmTrack lastfmTrack = lastfmCrawler.fetch("Adele", "Rolling in the Deep", youtubeAuthorization);
        System.out.println(lastfmTrack);
    }

    @Test
    public void get_found() throws Exception {
        LastfmTrack lastfmTrack = lastfmCrawler.fetch("Adele", "Chasing Pavements", youtubeAuthorization);
        System.out.println(lastfmTrack);
    }
}
