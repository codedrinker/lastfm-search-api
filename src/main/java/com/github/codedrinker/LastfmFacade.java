package com.github.codedrinker;

import com.github.codedrinker.api.LastfmAlbumApi;
import com.github.codedrinker.api.LastfmApi;
import com.github.codedrinker.api.LastfmArtistApi;
import com.github.codedrinker.api.LastfmTrackApi;
import com.github.codedrinker.config.Configuration;
import com.github.codedrinker.crawler.LastfmCrawler;
import com.github.codedrinker.crawler.LastfmSearchCrawler;
import com.github.codedrinker.dto.LastfmQuery;
import com.github.codedrinker.exception.LastfmCrawlerException;
import com.github.codedrinker.exception.LastfmException;
import com.github.codedrinker.helpers.Loader;
import com.github.codedrinker.model.LastfmCompositeResult;
import com.github.codedrinker.model.LastfmResult;
import com.github.codedrinker.model.LastfmTrack;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmFacade {
    private Configuration configuration;

    private Logger logger = LoggerFactory.getLogger(LastfmFacade.class);

    public LastfmFacade(Configuration configuration) {
        this.configuration = configuration;
    }

    public LastfmFacade() throws LastfmException {
        this.configuration = new Loader().load();
    }

    public LastfmResult searchTrack(LastfmQuery queryDTO) throws LastfmException {
        LastfmApi lastfmTrackApi = new LastfmTrackApi();
        if (queryDTO != null) {
            if (StringUtils.isBlank(queryDTO.getTrack())) {
                return null;
            }
            queryDTO.setMethod("track.search");
            queryDTO.setApi_key(configuration.getLastfmAppKey());
            try {
                return lastfmTrackApi.search(queryDTO);
            } catch (Exception e) {
                throw new LastfmException(e.getMessage(), e);
            }
        } else {
            return null;
        }
    }

    public LastfmResult searchArtist(LastfmQuery queryDTO) throws LastfmException {
        LastfmApi lastArtistApi = new LastfmArtistApi();
        if (queryDTO != null) {
            if (StringUtils.isBlank(queryDTO.getArtist())) {
                return null;
            }
            queryDTO.setMethod("artist.search");
            queryDTO.setApi_key(configuration.getLastfmAppKey());
            try {
                return lastArtistApi.search(queryDTO);
            } catch (Exception e) {
                throw new LastfmException(e.getMessage(), e);
            }
        } else {
            return null;
        }
    }

    public LastfmResult searchAlbum(LastfmQuery queryDTO) throws LastfmException {
        LastfmApi lastAlbumApi = new LastfmAlbumApi();
        if (queryDTO != null) {
            if (StringUtils.isBlank(queryDTO.getAlbum())) {
                return null;
            }
            queryDTO.setMethod("album.search");
            queryDTO.setApi_key(configuration.getLastfmAppKey());
            try {
                return lastAlbumApi.search(queryDTO);
            } catch (Exception e) {
                throw new LastfmException(e.getMessage(), e);
            }
        } else {
            return null;
        }
    }

    public LastfmCompositeResult search(LastfmQuery queryDTO) throws LastfmException {
        LastfmCompositeResult lastfmCompositeResult = new LastfmCompositeResult();

        LastfmSearchCrawler lastfmCrawler = new LastfmSearchCrawler();
        try {
            lastfmCompositeResult = lastfmCrawler.search(queryDTO.getQuery());
            return lastfmCompositeResult;
        } catch (LastfmCrawlerException e) {
            logger.error("crawler error -> {}", e);
        }

        if (StringUtils.isNotBlank(queryDTO.getTrack())) {
            LastfmQuery lastfmQuery = new LastfmQuery();
            lastfmQuery.setLimit(queryDTO.getLimit());
            lastfmQuery.setTrack(queryDTO.getQuery());
            lastfmQuery.setPage(queryDTO.getPage());
            lastfmCompositeResult.setTrack(searchTrack(lastfmQuery));
        }

        if (StringUtils.isNotBlank(queryDTO.getArtist())) {
            LastfmQuery lastfmQuery = new LastfmQuery();
            lastfmQuery.setLimit(queryDTO.getLimit());
            lastfmQuery.setArtist(queryDTO.getQuery());
            lastfmQuery.setPage(queryDTO.getPage());
            lastfmCompositeResult.setArtist(searchArtist(lastfmQuery));
        }

        if (StringUtils.isNotBlank(queryDTO.getAlbum())) {
            LastfmQuery lastfmQuery = new LastfmQuery();
            lastfmQuery.setLimit(queryDTO.getLimit());
            lastfmQuery.setAlbum(queryDTO.getQuery());
            lastfmQuery.setPage(queryDTO.getPage());
            lastfmCompositeResult.setAlbum(searchAlbum(lastfmQuery));
        }

        return lastfmCompositeResult;
    }

    public LastfmTrack fetchTrack(String artist, String track) throws LastfmException {
        return new LastfmCrawler().fetch(artist, track, configuration);
    }
}
