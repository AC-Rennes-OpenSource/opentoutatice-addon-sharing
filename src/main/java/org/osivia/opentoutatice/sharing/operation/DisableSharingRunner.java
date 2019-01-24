package org.osivia.opentoutatice.sharing.operation;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.security.ACP;
import org.osivia.opentoutatice.sharing.SharingConstants;

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
        // Remove sharing facet
        this.document.removeFacet(SharingConstants.FACET);
        this.session.saveDocument(this.document);

        // Remove sharing access control list
        ACP acp = this.document.getACP();
        acp.removeACL(SharingConstants.ACL);
        this.session.setACP(this.document.getRef(), acp, true);
    }

}
