package org.mbari.m3.vars.annotation.messages;

/**
 * @author Brian Schlining
 * @since 2017-06-02T11:49:00
 */
public class SeekMsg<T> {

    private final T index;

    public SeekMsg(T index) {
        this.index = index;
    }

    public T getIndex() {
        return index;
    }
}
