package org.mbari.m3.vars.annotation.ui.buttons;

import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.utils.MaterialIconFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.mbari.m3.vars.annotation.UIToolBox;
import org.mbari.m3.vars.annotation.commands.DuplicateAnnotationsCmd;
import org.mbari.m3.vars.annotation.events.AnnotationsSelectedEvent;
import org.mbari.m3.vars.annotation.model.Annotation;
import org.mbari.m3.vars.annotation.model.User;

/**
 * @author Brian Schlining
 * @since 2017-08-22T15:58:00
 */
public class DuplicateAnnotationBC {
    private final Button button;
    private final UIToolBox toolBox;

    public DuplicateAnnotationBC(Button button, UIToolBox toolBox) {
        this.button = button;
        this.toolBox = toolBox;
        init();
    }

    private void init() {
        button.setTooltip(new Tooltip(toolBox.getI18nBundle().getString("buttons.duplicate")));
        MaterialIconFactory iconFactory = MaterialIconFactory.get();
        Text icon = iconFactory.createIcon(MaterialIcon.FLIP_TO_BACK, "30px");
        button.setText(null);
        button.setGraphic(icon);
        button.setDisable(true);

        toolBox.getEventBus()
                .toObserverable()
                .ofType(AnnotationsSelectedEvent.class)
                .subscribe(e -> {
                    User user = toolBox.getData().getUser();
                    boolean enabled = (user != null) && e.get().size() > 0;
                    button.setDisable(!enabled);
                });

        button.setOnAction(e -> apply());

    }

    private void apply() {
        ObservableList<Annotation> annotations = toolBox.getData().getSelectedAnnotations();
        User user = toolBox.getData().getUser();
        toolBox.getEventBus()
                .send(new DuplicateAnnotationsCmd(user.getUsername(), annotations, true));
    }

}
