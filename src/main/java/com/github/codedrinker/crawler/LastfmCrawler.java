package com.github.codedrinker.crawler;

import com.github.codedrinker.auth.YoutubeAuthorization;
import com.github.codedrinker.model.LastfmTrack;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import org.apache.commons.lang3.StringUtils;
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
    private String endpoint = "https://www.last.fm/music/%s/_/%s";

    Logger logger = LoggerFactory.getLogger(LastfmCrawler.class);

    public LastfmTrack fetch(String artist, String track, YoutubeAuthorization youtubeAuthorization) {
        try {
            Connection connect = Jsoup.connect(String.format(endpoint, URLEncoder.encode(artist, "UTF-8"), URLEncoder.encode(track, "UTF-8")));
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
                    return fetchByAPI(artist, track, youtubeAuthorization);
                }
            } else {
                return fetchByAPI(artist, track, youtubeAuthorization);
            }
        } catch (Exception e) {
            logger.error("crawler fetch info by page error -> {}", e.getMessage());
        }
        return null;
    }

    private LastfmTrack fetchByAPI(String artist, String track, YoutubeAuthorization youtubeAuthorization) {
        try {
//            HttpTransport httpTransport = new ApacheHttpTransport.Builder().setProxy(new HttpHost("host", 5000)).build();
            HttpTransport httpTransport = new NetHttpTransport();
            YouTube youtube = new YouTube.Builder(httpTransport, new JacksonFactory(), null).setApplicationName(youtubeAuthorization.getName()).build();
            SearchListResponse response = youtube
                    .search()
                    .list("snippet")
                    .setQ(artist + " " + track)
                    .setType("video")
                    .setMaxResults(1L)
                    .setKey(youtubeAuthorization.getApiKey())
                    .execute();
            if (response.getItems() != null && response.getItems().size() != 0) {
                String videoId = response.getItems().get(0).getId().getVideoId();
                if (StringUtils.isNotBlank(videoId)) {
                    LastfmTrack lastfmTrack = new LastfmTrack();
                    lastfmTrack.setName(track);
                    lastfmTrack.setArtist(artist);
                    lastfmTrack.setYoutubeId(videoId);
                    return lastfmTrack;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("crawler fetch info by api error -> {}", e.getMessage());
        }
        return null;
    }
}
