package org.mbari.m3.vars.annotation.ui.mediadialog;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.mbari.m3.vars.annotation.model.Media;
import org.mbari.m3.vars.annotation.services.MediaService;

import javax.inject.Inject;
import java.util.ResourceBundle;

/**
 * Dialog for selecting VideoReferences (Media) from the {@link MediaService}
 *
 * @author Brian Schlining
 * @since 2017-06-01T14:11:00
 */
public class SelectMediaDialog extends Dialog<Media> {

    private final MediaService mediaService;
    private final ResourceBundle uiBundle;

    @Inject
    public SelectMediaDialog(MediaService mediaService, ResourceBundle uiBundle) {
        this.mediaService = mediaService;
        this.uiBundle = uiBundle;
        VideoBrowserPaneController controller = new VideoBrowserPaneController(mediaService, uiBundle);
        getDialogPane().setContent(controller.getRoot());
        ButtonType ok = new ButtonType(uiBundle.getString("global.ok"), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType(uiBundle.getString("global.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(ok, cancel);

        setResultConverter(buttonType -> {
            if (buttonType == ok) {
                return controller.getSelectedMedia().orElse(null);
            }
            else {
                return null;
            }
        });
    }

}
