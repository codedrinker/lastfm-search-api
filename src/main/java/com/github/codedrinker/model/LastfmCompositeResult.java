package com.github.codedrinker.model;

/**
 * Created by codedrinker on 31/07/2017.
 */
public class LastfmCompositeResult extends AbstractLastfm {
    private LastfmResult artist;
    private LastfmResult album;
    private LastfmResult track;

    public LastfmResult getArtist() {
        return artist;
    }

    public void setArtist(LastfmResult artist) {
        this.artist = artist;
    }

    public LastfmResult getAlbum() {
        return album;
    }

    public void setAlbum(LastfmResult album) {
        this.album = album;
    }

    public LastfmResult getTrack() {
        return track;
    }

    public void setTrack(LastfmResult track) {
        this.track = track;
    }
}
