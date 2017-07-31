package com.github.codedrinker.api;

import com.github.codedrinker.dto.LastfmQuery;
import com.github.codedrinker.exception.LastfmException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.client.utils.URIBuilder;

import java.util.Map;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmQueryStringBuilder {
    public static String build(String host, String path, LastfmQuery lastfmQuery) throws LastfmException {
        try {
            Map<String, String> describe = BeanUtils.describe(lastfmQuery);
            final URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(host).setPath(path);
            describe.forEach((k, v) -> {
                if (!k.equals("class") && v != null) {
                    uriBuilder.addParameter(k, v);
                }
            });
            return uriBuilder.toString();
        } catch (Exception e) {
            throw new LastfmException(e.getMessage(), e);
        }
    }
}
