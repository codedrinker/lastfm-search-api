package com.github.codedrinker.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.codedrinker.config.Configuration;
import com.github.codedrinker.exception.LastfmException;
import com.github.codedrinker.model.LastfmTrack;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * Created by codedrinker on 01/08/2017.
 */
public class LastfmCrawler {
    private String lastfmEndpoint = "https://www.last.fm/music/%s/_/%s";
    private String youtubeEndpoint = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=%s&type=video&fields=%s&key=%s";

    Logger logger = LoggerFactory.getLogger(LastfmCrawler.class);

    public LastfmTrack fetch(String artist, String track, Configuration configuration) {
        try {
            Connection connect = Jsoup.connect(String.format(lastfmEndpoint, URLEncoder.encode(artist, "UTF-8"), URLEncoder.encode(track, "UTF-8")));
            connect.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            Document document = connect.get();
            Elements elements = document.select(".col-main .video-preview a.image-overlay-playlink-link");
            if (elements != null && elements.size() != 0) {
                Element element = elements.get(0);
                String youtubeId = element.attr("data-youtube-id");
                if (StringUtils.isNotBlank(youtubeId)) {
                    LastfmTrack lastfmTrack = new LastfmTrack();
                    lastfmTrack.setArtist(artist);
                    lastfmTrack.setName(track);
                    lastfmTrack.setYoutubeId(youtubeId);
                    return lastfmTrack;
                } else {
                    return fetchByAPI(artist, track, configuration);
                }
            } else {
                return fetchByAPI(artist, track, configuration);
            }
        } catch (Exception e) {
            logger.error("crawler fetch info by page error -> {}", e.getMessage());
        }
        return null;
    }

    private LastfmTrack fetchByAPI(String artist, String track, Configuration configuration) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String api = String.format(youtubeEndpoint, URLEncoder.encode(artist + " " + track, "UTF-8"), URLEncoder.encode("items/id", "UTF-8"), configuration.getLastfmYoutubeAppKey());
            logger.info("search youtube api : {}", api);
            HttpGet httpGet = new HttpGet(api);
            httpGet.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200) {
                    String res = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObject = JSON.parseObject(res);
                    JSONArray items = jsonObject.getJSONArray("items");
                    if (items != null && items.size() != 0) {
                        JSONObject video = (JSONObject) items.get(0);
                        String string = video.getJSONObject("id").getString("videoId");
                        if (StringUtils.isNotBlank(string)) {
                            LastfmTrack lastfmTrack = new LastfmTrack();
                            lastfmTrack.setName(track);
                            lastfmTrack.setArtist(artist);
                            lastfmTrack.setYoutubeId(string);
                            return lastfmTrack;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }

                } else {
                    throw new LastfmException(response.getEntity().toString());
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error("crawler fetch info by api error -> {}", e.getMessage());
        }
        return null;
    }
}
