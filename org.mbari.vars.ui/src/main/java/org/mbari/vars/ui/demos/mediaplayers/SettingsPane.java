package org.mbari.vars.ui.demos.mediaplayers;

import javafx.scene.layout.Pane;
import org.mbari.vars.ui.demos.javafx.prefs.IPrefs;

/**
 * @author Brian Schlining
 * @since 2017-12-28T13:06:00
 */
public interface SettingsPane extends IPrefs {

    String getName();

    Pane getPane();
}
