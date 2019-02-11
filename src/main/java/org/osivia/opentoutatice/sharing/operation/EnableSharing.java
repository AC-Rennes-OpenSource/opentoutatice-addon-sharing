package org.osivia.opentoutatice.sharing.operation;

import java.security.Principal;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;

/**
 * Enable sharing operation.
 * 
 * @author Cédric Krommenhoek
 */
@Operation(id = EnableSharing.ID, category = Constants.CAT_DOCUMENT, label = "Enable sharing")
public class EnableSharing {

    /** Operation identifier. */
    public static final String ID = "Document.EnableSharing";
    
    
    /** Core session. */
    @Context
    private CoreSession session;

    /** Link identifier. */
    @Param(name = "linkId", required = true, description = "Link identifier")
    private String linkId;

    /** Sharing permission. */
    @Param(name = "permission", required = true, description = "Sharing permission")
    private String permission;


    /**
     * Constructor.
     */
    public EnableSharing() {
        super();
    }


    /**
     * Run operation.
     * 
     * @param document document
     */
    @OperationMethod
    public void run(DocumentModel document) {
        // Sharing author
        Principal principal = this.session.getPrincipal();
        String author;
        if (principal == null) {
            author = null;
        } else {
            author = principal.getName();
        }

        // Silent run
        SharingRunner runner = new EnableSharingRunner(this.session, document, author, this.linkId, this.permission);
        runner.silentRun(false);
    }

}
