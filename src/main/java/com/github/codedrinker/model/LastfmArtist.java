package com.github.codedrinker.model;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmArtist extends AbstractLastfm {
    private Long listeners;

    public Long getListeners() {
        return listeners;
    }

    public void setListeners(Long listeners) {
        this.listeners = listeners;
    }
}
