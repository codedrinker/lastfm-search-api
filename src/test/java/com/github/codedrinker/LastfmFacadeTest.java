package com.github.codedrinker;

import com.github.codedrinker.dto.LastfmQuery;
import com.github.codedrinker.model.LastfmCompositeResult;
import com.github.codedrinker.model.LastfmResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmFacadeTest {
    LastfmFacade lastfmFacade;

    @Before
    public void setUp() throws Exception {
        lastfmFacade = new LastfmFacade();
    }

    @Test
    public void searchTrack() throws Exception {
        LastfmQuery queryDTO = new LastfmQuery();
        queryDTO.setTrack("Hello");
        queryDTO.setLimit(3);
        LastfmResult lastfmResult = lastfmFacade.searchTrack(queryDTO);
        Assert.assertEquals(3, lastfmResult.getTracks().size());
        Assert.assertTrue(lastfmResult.getTracks().get(0).getName().contains("Hello"));
    }

    @Test
    public void searchTrack4Chinese() throws Exception {
        LastfmQuery queryDTO = new LastfmQuery();
        queryDTO.setTrack("童话镇");
        queryDTO.setLimit(1);
        LastfmResult lastfmResult = lastfmFacade.searchTrack(queryDTO);
        Assert.assertEquals(1, lastfmResult.getTracks().size());
        Assert.assertTrue(StringUtils.containsIgnoreCase(lastfmResult.getTracks().get(0).getName(),"童话镇"));
    }

    @Test
    public void searchArtist() throws Exception {
        LastfmQuery queryDTO = new LastfmQuery();
        queryDTO.setArtist("Hello");
        queryDTO.setLimit(3);
        LastfmResult lastfmResult = lastfmFacade.searchArtist(queryDTO);
        Assert.assertEquals(3, lastfmResult.getArtists().size());
        Assert.assertTrue(lastfmResult.getArtists().get(0).getName().contains("Hello"));
    }

    @Test
    public void searchAlbum() throws Exception {
        LastfmQuery queryDTO = new LastfmQuery();
        queryDTO.setAlbum("Hello");
        queryDTO.setLimit(3);
        LastfmResult lastfmResult = lastfmFacade.searchAlbum(queryDTO);
        Assert.assertEquals(3, lastfmResult.getAlbums().size());
        Assert.assertTrue(lastfmResult.getAlbums().get(0).getName().contains("Hello"));
    }

    @Test
    public void search() throws Exception {
        LastfmQuery queryDTO = new LastfmQuery();
        queryDTO.setQuery("Hello");
        queryDTO.setLimit(3);
        LastfmCompositeResult lastfmResult = lastfmFacade.search(queryDTO);
        Assert.assertNotNull(lastfmResult.getAlbum());
        Assert.assertTrue(StringUtils.containsIgnoreCase(lastfmResult.getAlbum().getAlbums().get(0).getName(), "hello"));
        Assert.assertNotNull(lastfmResult.getArtist());
        Assert.assertTrue(StringUtils.containsIgnoreCase(lastfmResult.getArtist().getArtists().get(0).getName(), "hello"));
        Assert.assertNotNull(lastfmResult.getTrack());
        Assert.assertTrue(StringUtils.containsIgnoreCase(lastfmResult.getTrack().getTracks().get(0).getName(), "hello"));
    }
}
