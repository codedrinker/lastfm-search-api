package com.github.codedrinker.model;

import java.util.List;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmResult extends AbstractLastfm {
    private long total;
    private int page;
    private List<LastfmTrack> tracks;
    private List<LastfmAlbum> albums;
    private List<LastfmArtist> artists;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<LastfmArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<LastfmArtist> artists) {
        this.artists = artists;
    }

    public List<LastfmAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(List<LastfmAlbum> albums) {
        this.albums = albums;
    }

    public List<LastfmTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<LastfmTrack> tracks) {
        this.tracks = tracks;
    }
}
