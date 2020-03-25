package org.mbari.vars.ui.javafx.localization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.disposables.Disposable;
import org.mbari.vars.core.EventBus;
import org.mbari.vars.services.model.Annotation;
import org.mbari.vars.services.model.Association;
import org.mbari.vars.ui.events.AnnotationsAddedEvent;
import org.mbari.vars.ui.events.AnnotationsChangedEvent;
import org.mbari.vars.ui.events.AnnotationsRemovedEvent;
import org.mbari.vcr4j.sharktopoda.client.gson.DurationConverter;
import org.mbari.vcr4j.sharktopoda.client.localization.IO;
import org.mbari.vcr4j.sharktopoda.client.localization.Localization;
import org.mbari.vcr4j.sharktopoda.client.localization.Message;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Brian Schlining
 * @since 2020-03-05T17:02:00
 */
public class LocalizationController {
    private final EventBus eventBus;
    private final LocalizationSettings localizationSettings;
    private final IO io;
    private final List<Disposable> disposables = new ArrayList<>();
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .registerTypeAdapter(Duration.class, new DurationConverter())
            .create();

    public LocalizationController(EventBus eventBus, LocalizationSettings settings) {
        this.eventBus = eventBus;
        this.localizationSettings = settings;
        disposables.add(eventBus.toObserverable()
                .ofType(AnnotationsAddedEvent.class)
                .filter(evt -> evt.getEventSource() != this)
                .subscribe(this::handleAddedLocally));
        disposables.add(eventBus.toObserverable()
                .ofType(AnnotationsRemovedEvent.class)
                .filter(evt -> evt.getEventSource() != this)
                .subscribe(this::handleRemovedLocally));
        disposables.add(eventBus.toObserverable()
                .ofType(AnnotationsChangedEvent.class)
                .filter(evt -> evt.getEventSource() != this)
                .subscribe(this::handleChangedLocally));
        this.io = new IO(settings.getIncomingPort(), settings.getOutgoingPort(),
                settings.getIncomingTopic(), settings.getOutgoingTopic());

        disposables.add(io.getController()
                .getIncoming()
                .ofType(Message.class)
                .subscribe(this::handleIncomingMessage));


    }

    public void handleAddedLocally(AnnotationsAddedEvent evt) {

    }

    public void handleRemovedLocally(AnnotationsRemovedEvent evt) {

    }

    public void handleChangedLocally(AnnotationsChangedEvent evt) {

    }

    public void handleIncomingMessage(Message message) {

    }

    public void close() {
        disposables.forEach(Disposable::dispose);
        io.close();
    }

    public List<Localization> annotationsToLocalizations(Collection<Annotation> annotations) {
        List<Localization> xs = new ArrayList<>();
        for (Annotation a: annotations) {
            for (Association ass: a.getAssociations()) {
                if (ass.getLinkName().equalsIgnoreCase("bounding box") &&
                        ass.getMimeType().equalsIgnoreCase("application/json")) {
                    BoundingBox box = gson.fromJson(ass.getLinkValue(), BoundingBox.class);
                    Localization x = new Localization(a.getConcept(),
                            a.getElapsedTime(),
                            ass.getUuid(),
                            a.getVideoReferenceUuid(),
                            box.getX(),
                            box.getY(),
                            box.getWidth(),
                            box.getHeight(),
                            a.getDuration(),
                            a.getObservationUuid());
                    xs.add(x);
                }
            }
        }
        return xs;
    }




}