package com.github.codedrinker.model;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmTrack extends AbstractLastfm {
    private String artist;
    private Long listeners;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Long getListeners() {
        return listeners;
    }

    public void setListeners(Long listeners) {
        this.listeners = listeners;
    }
}