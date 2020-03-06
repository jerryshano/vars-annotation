package org.mbari.vars.ui.javafx.localization;

import java.util.prefs.Preferences;

/**
 * Saves and loads localization preferences. Default values are defined in reference.conf and
 * application.conf or passed in at the command line.
 *
 * @author Brian Schlining
 * @since 2020-03-05T16:15:00
 */
public class LocalizationPrefs {
    private static Preferences prefs = Preferences.userNodeForPackage(LocalizationPrefs.class);
    private static final String IN_PORT_KEY = "incoming-port";
    private static final String IN_TOPIC_KEY = "incoming-topic";
    private static final String OUT_PORT_KEY = "outgoing-port";
    private static final String OUT_TOPIC_KEY = "outgoing-topic";

    /**
     * Saves settings to preferences (local)
     * @param settings The settings to save to prefs
     */
    public static void save(LocalizationSettings settings) {
        prefs.putInt(IN_PORT_KEY, settings.getIncomingPort());
        prefs.put(IN_TOPIC_KEY, settings.getIncomingTopic());
        prefs.putInt(OUT_PORT_KEY, settings.getOutgoingPort());
        prefs.put(OUT_TOPIC_KEY, settings.getOutgoingTopic());
    }

    /**
     * Loads settings from preferences (local). Use values defined in your configuration
     * (reference.conf/application.conf) as defaults.
     * @param settings The default settings.
     * @return A new settings object with values loaded from local preferences. Default values
     *         are supplied by the argument.
     */
    public static LocalizationSettings load(LocalizationSettings settings) {
        int incomingPort = prefs.getInt(IN_PORT_KEY, settings.getIncomingPort());
        String incomingTopic = prefs.get(IN_TOPIC_KEY, settings.getIncomingTopic());
        int outgoingPort = prefs.getInt(OUT_PORT_KEY, settings.getOutgoingPort());
        String outgoingTopic = prefs.get(OUT_TOPIC_KEY, settings.getOutgoingTopic());
        return new LocalizationSettings(incomingPort, incomingTopic, outgoingPort, outgoingTopic);
    }
}
