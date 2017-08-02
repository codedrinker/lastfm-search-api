package com.github.codedrinker.model;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by codedrinker on 31/07/2017.
 */
public abstract class AbstractLastfm {
    private String name;
    private String url;
    private String streamable;
    private List<LastfmImage> image;
    private String mbid;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStreamable() {
        return streamable;
    }

    public void setStreamable(String streamable) {
        this.streamable = streamable;
    }

    public List<LastfmImage> getImage() {
        return image;
    }

    public void setImage(List<LastfmImage> image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, true);
    }
}
