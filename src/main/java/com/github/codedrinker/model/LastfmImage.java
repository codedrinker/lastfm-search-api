package com.github.codedrinker.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmImage extends AbstractLastfm {
    @JSONField(name = "#text")
    private String text;
    private String size;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
