package org.osivia.opentoutatice.sharing.operation;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;

/**
 * Enable sharing runner.
 * 
 * @author Cédric Krommenhoek
 * @see SharingRunner
 */
public class EnableSharingRunner extends SharingRunner {

    /**
     * Constructor.
     * 
     * @param session core session
     * @param document document
     */
    public EnableSharingRunner(CoreSession session, DocumentModel document) {
        super(session, document);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void run() throws ClientException {
        this.document.addFacet(FACET);
        this.session.saveDocument(this.document);
    }

}
