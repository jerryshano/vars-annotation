package org.mbari.m3.vars.annotation.mediaplayers;

import javafx.util.Pair;
import org.mbari.m3.vars.annotation.UIToolBox;
import org.mbari.m3.vars.annotation.events.MediaChangedEvent;
import org.mbari.m3.vars.annotation.events.MediaPlayerChangedEvent;
import org.mbari.m3.vars.annotation.mediaplayers.sharktopoda.Sharktopoda;
import org.mbari.m3.vars.annotation.mediaplayers.sharktopoda.SharktopodaPaneController;
import org.mbari.m3.vars.annotation.model.Media;
import org.mbari.vcr4j.VideoError;
import org.mbari.vcr4j.VideoIO;
import org.mbari.vcr4j.VideoState;
import org.mbari.vcr4j.decorators.SchedulerVideoIO;
import org.mbari.vcr4j.decorators.StatusDecorator;
import org.mbari.vcr4j.decorators.VCRSyncDecorator;
import org.mbari.vcr4j.sharktopoda.SharktopodaError;
import org.mbari.vcr4j.sharktopoda.SharktopodaState;
import org.mbari.vcr4j.sharktopoda.SharktopodaVideoIO;
import org.mbari.vcr4j.sharktopoda.commands.OpenCmd;
import org.mbari.vcr4j.sharktopoda.decorators.FauxTimecodeDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * @author Brian Schlining
 * @since 2017-08-07T10:50:00
 */
public class MediaPlayers {

    private final UIToolBox toolBox;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Sharktopoda sharktopoda = new Sharktopoda();

    public MediaPlayers(UIToolBox toolBox) {
        this.toolBox = toolBox;
        toolBox.getEventBus()
                .toObserverable()
                .ofType(MediaChangedEvent.class)
                .subscribe(e -> open(e.get()));

        Runtime.getRuntime()
                .addShutdownHook(new Thread(this::close));
    }

    private void open(Media media) {

        // Close the old one
        close();

        String u = media.getUri().toString();
        if (u.startsWith("urn:tid")) {
            // handle tape
        }
        else if (u.startsWith("http")) {
            // TODO handlers should be register so that user can select his/her preferred
            // handler in preferences
            try {
                openSharktopoda(media);
            } catch (Exception e) {
                log.warn("Failed to open " + media.getUri(), e);
            }
        }

    }

    private void openSharktopoda(Media media) {
        Pair<Integer, Integer> portNumbers = SharktopodaPaneController.getPortNumbers();
        sharktopoda.open(media, portNumbers.getKey(), portNumbers.getValue())
                .thenAccept(mediaPlayer ->  toolBox.getEventBus()
                            .send(new MediaPlayerChangedEvent(MediaPlayers.this, mediaPlayer))
                );
    }

    private void close() {
        // Close the old MediaPlayer
        log.debug("Closing MediaPlayer: " + toolBox.getMediaPlayer());
        Optional.ofNullable(toolBox.getMediaPlayer())
                .ifPresent(MediaPlayer::close);
    }
}
