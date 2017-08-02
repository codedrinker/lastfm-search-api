package com.github.codedrinker.config;

/**
 * Created by codedrinker on 02/08/2017.
 */
public class Configuration {
    private String lastfmAppKey;
    private String lastfmYoutubeAppKey;
    private String lastfmYoutubeAppName;
    
    public String getLastfmAppKey() {
        return lastfmAppKey;
    }

    public void setLastfmAppKey(String lastfmAppKey) {
        this.lastfmAppKey = lastfmAppKey;
    }

    public String getLastfmYoutubeAppKey() {
        return lastfmYoutubeAppKey;
    }

    public void setLastfmYoutubeAppKey(String lastfmYoutubeAppKey) {
        this.lastfmYoutubeAppKey = lastfmYoutubeAppKey;
    }

    public String getLastfmYoutubeAppName() {
        return lastfmYoutubeAppName;
    }

    public void setLastfmYoutubeAppName(String lastfmYoutubeAppName) {
        this.lastfmYoutubeAppName = lastfmYoutubeAppName;
    }
}
