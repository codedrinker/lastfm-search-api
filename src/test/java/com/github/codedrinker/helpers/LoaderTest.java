package com.github.codedrinker.helpers;

import com.github.codedrinker.config.Configuration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by codedrinker on 02/08/2017.
 */
public class LoaderTest {
    @Test
    public void load() throws Exception {
        Loader loader = new Loader();
        Configuration configuration = loader.load();
        Assert.assertEquals("lastfm-appkey", configuration.getLastfmAppKey());
        Assert.assertEquals("lastfm-youtube-appkey", configuration.getLastfmYoutubeAppKey());
        Assert.assertEquals("lastfm", configuration.getLastfmYoutubeAppName());
    }
}
