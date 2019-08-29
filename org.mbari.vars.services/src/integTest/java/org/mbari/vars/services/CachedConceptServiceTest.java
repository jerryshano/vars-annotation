package org.mbari.vars.services;

import org.junit.Test;
import org.mbari.vars.services.impl.varskbserver.v1.KBConceptService;
import org.mbari.vars.services.impl.varskbserver.v1.KBWebServiceFactory;
import org.mbari.vars.services.model.Concept;
import org.mbari.vars.services.model.ConceptDetails;


import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Brian Schlining
 * @since 2017-05-16T11:15:00
 */
public class CachedConceptServiceTest {

    ConceptService conceptService = TestToolbox.getServices().getConceptService();

    @Test
    public void testFetchConceptTree() throws InterruptedException, ExecutionException {
        CompletableFuture<Concept> f = conceptService.findRoot();
        while (!f.isDone()) {
            Thread.sleep(20);
        }
        Concept c = f.get();
        assertNotNull(c);
    }

    @Test
    public void testFindDetails() throws InterruptedException, ExecutionException  {
        //CompletableFuture<Concept> f0 = conceptService.findRoot();
        CompletableFuture<Concept> f0 = conceptService.findConcept("Nanomia").thenApply(a -> a.get());
        while (!f0.isDone()) {
            Thread.sleep(20);
        }
        Concept c = f0.get();
        assertNotNull(c);

        CompletableFuture<Optional<ConceptDetails>> f1 = conceptService.findDetails(c.getName());
        while (!f1.isDone()) {
            Thread.sleep(20);
        }
        Optional<ConceptDetails> cd = f1.get();
        assertTrue(cd.isPresent());


        CompletableFuture<Optional<ConceptDetails>> f2 = conceptService.findDetails("Pandalus platyceros");
        while (!f2.isDone()) {
            Thread.sleep(20);
        }
        Optional<ConceptDetails> n = f2.get();
        assertTrue(n.isPresent());
    }
}
