package com.github.codedrinker.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.codedrinker.dto.LastfmQuery;
import com.github.codedrinker.exception.LastfmException;
import com.github.codedrinker.model.LastfmResult;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by codedrinker on 28/07/2017.
 */
public abstract class LastfmApi {
    protected final String host = "ws.audioscrobbler.com";
    Logger logger = LoggerFactory.getLogger(LastfmApi.class);

    public LastfmResult search(LastfmQuery lastfmQuery) throws LastfmException, IOException {
        String endpoint = LastfmQueryStringBuilder.build(host, path(), lastfmQuery);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(endpoint);
        logger.info("search api : {}", endpoint);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200) {
                String res = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(res);
                LastfmResult lastfmResult = new LastfmResult();
                if (jsonObject.getJSONObject("results") == null) {
                    return null;
                }
                JSONObject result = jsonObject.getJSONObject("results");

                if (result.containsKey("opensearch:Query")) {
                    lastfmResult.setPage(result.getJSONObject("opensearch:Query").getInteger("startPage"));
                }
                if (result.containsKey("opensearch:totalResults")) {
                    lastfmResult.setTotal(result.getLong("opensearch:totalResults"));
                }
                parse(result, lastfmResult);
                return lastfmResult;

            } else {
                throw new LastfmException(response.getEntity().toString());
            }
        } finally {
            response.close();
        }
    }

    protected abstract String path();

    protected abstract void parse(JSONObject jsonObject, LastfmResult lastfmResult);
}
