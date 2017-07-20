package org.mbari.m3.vars.annotation.commands;

import com.google.common.base.Preconditions;
import org.mbari.m3.vars.annotation.model.Annotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Schlining
 * @since 2017-05-11T13:06:00
 */
public class DeleteAnnotations implements Command {

    private final List<Annotation> annotations;

    public DeleteAnnotations(List<Annotation> annotations) {
        Preconditions.checkArgument(annotations != null,
                "Can not delete a null annotation list");
        Preconditions.checkArgument(!annotations.isEmpty(),
                "Can not delete an empty annotation list");
        this.annotations = Collections.unmodifiableList(new ArrayList<>(annotations));
    }

    @Override
    public void apply() {

    }

    @Override
    public void unapply() {

    }

    @Override
    public String getDescription() {
        return "Delete " + annotations.size() + " annotations";
    }
}