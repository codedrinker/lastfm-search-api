package com.github.codedrinker.helpers;

import com.github.codedrinker.config.Configuration;
import com.github.codedrinker.exception.LastfmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by codedrinker on 02/08/2017.
 */
public class Loader {
    private static Configuration configuration;
    private static final String DEFAULT_CONFIGURATION_FILE = "lastfm.properties";
    private Logger logger = LoggerFactory.getLogger(Loader.class);

    private Lock lock = new ReentrantLock(true);

    public Configuration load() throws LastfmException {
        lock.lock();

        if (configuration != null) {
            return configuration;
        }

        ClassLoader classLoader = Loader.class.getClassLoader();
        Properties props = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = classLoader.getResourceAsStream(DEFAULT_CONFIGURATION_FILE);
            props.load(inputStream);
            configuration = new Configuration();
            configuration.setLastfmAppKey(props.getProperty("lastfm.appKey"));
            configuration.setLastfmYoutubeAppKey(props.getProperty("lastfm.youtube.appKey"));
            configuration.setLastfmYoutubeAppName(props.getProperty("lastfm.youtube.appName"));
            inputStream.close();
        } catch (Exception e) {
            if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            logger.error("Could not read configuration file [" + DEFAULT_CONFIGURATION_FILE + "].", e);
            return configuration;
        } finally {
            lock.unlock();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (InterruptedIOException ignore) {
                    Thread.currentThread().interrupt();
                } catch (Throwable ignore) {
                }

            }
        }
        return configuration;
    }
}
