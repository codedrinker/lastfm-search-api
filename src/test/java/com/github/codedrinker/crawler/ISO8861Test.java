package com.github.codedrinker.crawler;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.util.Date;

/**
 * Created by codedrinker on 09/08/2017.
 */
public class ISO8861Test {
    @Test
    public void test() throws Exception {
        Duration dur = DatatypeFactory.newInstance().newDuration("PT21M3S");
        long timeInMillis = dur.getTimeInMillis(new Date());
        Assert.assertEquals("1263000", String.valueOf(timeInMillis));
    }
}
