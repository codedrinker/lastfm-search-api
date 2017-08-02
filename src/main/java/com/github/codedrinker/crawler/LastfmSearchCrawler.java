package com.github.codedrinker.crawler;

import com.github.codedrinker.exception.LastfmCrawlerException;
import com.github.codedrinker.model.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by codedrinker on 31/07/2017.
 */
public class LastfmSearchCrawler {
    private String queryEndpoint = "https://www.last.fm/search?q=%s";

    Logger logger = LoggerFactory.getLogger(LastfmSearchCrawler.class);

    public LastfmCompositeResult search(String query) throws LastfmCrawlerException {
        LastfmCompositeResult lastfmCompositeResult = new LastfmCompositeResult();
        try {
            Connection connect = Jsoup.connect(String.format(queryEndpoint, URLEncoder.encode(query, "UTF-8")));
            connect.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            Document document = connect.get();
            Elements container = document.getElementsByClass("page-content");
            if (container.size() != 0) {
                Elements sections = container.get(0).getElementsByTag("section");
                lastfmCompositeResult.setArtist(extractArtists(sections.get(0)));
                lastfmCompositeResult.setAlbum(extractAlbums(sections.get(1)));
                lastfmCompositeResult.setTrack(extractTracks(sections.get(2)));

            } else {
                throw new LastfmCrawlerException("no results");
            }
        } catch (Exception e) {
            logger.error("crawler by webpage error -> {}", e);
            throw new LastfmCrawlerException(e.getMessage());
        }
        return lastfmCompositeResult;
    }

    private LastfmResult extractTracks(Element element) {
        Elements tracks = element.getElementsByClass("chartlist-name");
        LastfmResult result = new LastfmResult();
        result.setTracks(new ArrayList<LastfmTrack>());
        for (Element track : tracks) {
            String artist = track.getElementsByClass("chartlist-artists").text();
            String name = track.getElementsByClass("link-block-target").text();
            LastfmTrack lastfmTrack = new LastfmTrack();
            lastfmTrack.setArtist(artist);
            lastfmTrack.setName(name);
            result.getTracks().add(lastfmTrack);
        }
        return result;
    }

    private LastfmResult extractAlbums(Element element) {
        Elements albums = element.getElementsByTag("li");
        LastfmResult result = new LastfmResult();
        result.setAlbums(new ArrayList<LastfmAlbum>());
        for (Element album : albums) {
            String avatar = extractImage(album);
            String name = extractName(album);
            LastfmAlbum lastfmAlbum = new LastfmAlbum();
            final LastfmImage lastfmImage = new LastfmImage();
            lastfmImage.setSize("medium");
            lastfmImage.setText(avatar);
            lastfmAlbum.setImage(new ArrayList<LastfmImage>() {{
                this.add(lastfmImage);
            }});
            lastfmAlbum.setName(name);
            result.getAlbums().add(lastfmAlbum);
        }
        return result;
    }

    private LastfmResult extractArtists(Element element) {
        Elements artists = element.getElementsByTag("li");
        LastfmResult artistResult = new LastfmResult();
        artistResult.setArtists(new ArrayList<LastfmArtist>());
        for (Element artist : artists) {
            String avatar = extractImage(artist);
            String name = extractName(artist);
            LastfmArtist lastfmArtist = new LastfmArtist();
            final LastfmImage lastfmImage = new LastfmImage();
            lastfmImage.setSize("medium");
            lastfmImage.setText(avatar);
            lastfmArtist.setImage(new ArrayList<LastfmImage>() {{
                this.add(lastfmImage);
            }});
            lastfmArtist.setName(name);
            artistResult.getArtists().add(lastfmArtist);
        }
        return artistResult;
    }

    private String extractName(Element artist) {
        return artist.getElementsByClass("link-block-target").text();
    }

    private String extractImage(Element artist) {
        return artist.getElementsByClass("grid-items-cover-image-image").get(0).getElementsByTag("img").get(0).attr("src");
    }
}
