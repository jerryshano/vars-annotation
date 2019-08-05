package org.mbari.vars.services.util;

import org.junit.Test;
import static org.junit.Assert.*;

import org.mbari.vars.services.ServicesBuilder;
import org.mbari.vars.ui.Initializer;
import org.mbari.vars.services.util.WebPreferencesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.prefs.Preferences;

/**
 * @author Brian Schlining
 * @since 2017-10-18T10:05:00
 */
public class WebPreferencesTest {

    private final Logger log = LoggerFactory.getLogger(getClass());


    WebPreferencesFactory factory = new WebPreferencesFactory(Initializer.getToolBox()
            .getServices()
            .getPreferencesService(), 10 * 1000L);

    @Test
    public void testNode() {
        Preferences userRoot = factory.remoteUserRoot("brian");
        log.debug("userRoot = " + userRoot.absolutePath());
        userRoot.put("trash", "foo");
        String s0 = userRoot.get("trash", "bar");
        assertTrue("Returned value was not what we expected", s0.equals("foo"));
        userRoot.put("trash", "bar");
        String s1 = userRoot.get("trash", "bar");
        assertTrue("Returned value was not what we expected", s1.equals("bar"));
        userRoot.remove("trash");

    }

}
