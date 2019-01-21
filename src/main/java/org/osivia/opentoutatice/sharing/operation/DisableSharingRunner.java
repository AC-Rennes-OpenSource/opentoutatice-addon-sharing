package org.osivia.opentoutatice.sharing.operation;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;

/**
 * Disable sharing runner.
 * 
 * @author Cédric Krommenhoek
 * @see SharingRunner
 */
public class DisableSharingRunner extends SharingRunner {

    /**
     * Constructor.
     * 
     * @param session core session
     * @param document document
     */
    public DisableSharingRunner(CoreSession session, DocumentModel document) {
        super(session, document);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void run() throws ClientException {
        this.document.removeFacet(FACET);
        this.session.saveDocument(this.document);
    }

}
