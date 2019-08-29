package org.mbari.vars.services.varskbserver.v1;

import org.junit.Test;
import static org.junit.Assert.*;


import org.mbari.vars.services.ConceptService;
import org.mbari.vars.services.TestToolbox;
import org.mbari.vars.services.model.Concept;
import org.mbari.vars.services.model.ConceptDetails;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author Brian Schlining
 * @since 2017-05-16T09:33:00
 */
public class KBConceptServiceTest {

    String endpoint = "http://localhost:8083/kb/v1/";
    //KBConceptService conceptService = Initializer.getInjector().getInstance(KBConceptService.class);
    ConceptService conceptService = TestToolbox.getServices().getConceptService();


    @Test
    public void findRoot() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Concept> f = conceptService.findRoot();
        Concept root = f.get(10000, TimeUnit.MILLISECONDS);
        assertNotNull(root);
    }

    @Test
    public void testFindAllNames() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<List<String>> f = conceptService.findAllNames();
        List<String> allNames = f.get(20000, TimeUnit.MILLISECONDS);
        assertNotNull(allNames);
    }

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
        CompletableFuture<Concept> f0 = conceptService.findRoot();
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
