package org.mbari.vars.ui.messages;

/**
 * @author Brian Schlining
 * @since 2017-07-28T13:34:00
 */
public class SetProgress implements Message {
    private double progress;

    public SetProgress(double progress) {
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return "SetProgress{" +
                "progress=" + progress +
                '}';
    }
}
