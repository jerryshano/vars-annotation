package org.mbari.vars.ui.messages;

/**
 * @author Brian Schlining
 * @since 2017-05-17T10:45:00
 */
public class ShowConceptInTreeViewMsg {

    private final String name;

    public ShowConceptInTreeViewMsg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
