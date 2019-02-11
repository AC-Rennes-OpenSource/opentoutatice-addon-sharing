package org.osivia.opentoutatice.sharing.operation;

import java.io.IOException;

import org.nuxeo.ecm.automation.core.util.DocumentHelper;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.osivia.opentoutatice.sharing.SharingConstants;

/**
 * Enable sharing runner.
 * 
 * @author Cédric Krommenhoek
 * @see SharingRunner
 */
public class EnableSharingRunner extends SharingRunner {

    /** Sharing author. */
    private final String author;
    /** Sharing link identifier. */
    private final String linkId;
    /** Sharing link permission. */
    private final String permission;


    /**
     * Constructor.
     * 
     * @param session core session
     * @param document document
     * @param author sharing author
     * @param linkId sharing link identifier
     * @param permission sharing link permission
     */
    public EnableSharingRunner(CoreSession session, DocumentModel document, String author, String linkId, String permission) {
        super(session, document);
        this.author = author;
        this.linkId = linkId;
        this.permission = permission;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void run() throws ClientException {
        // Add sharing facet
        this.document.addFacet(SharingConstants.FACET);

        // Set properties
        try {
            DocumentHelper.setProperty(this.session, this.document, SharingConstants.SHARING_AUTHOR_XPATH, this.author);
            DocumentHelper.setProperty(this.session, this.document, SharingConstants.SHARING_LINK_ID_XPATH, this.linkId);
            DocumentHelper.setProperty(this.session, this.document, SharingConstants.SHARING_LINK_PERMISSION_XPATH, this.permission);
        } catch (IOException e) {
            throw new ClientException(e);
        }

        this.session.saveDocument(this.document);
    }

}
