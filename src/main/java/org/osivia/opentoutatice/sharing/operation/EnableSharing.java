package org.osivia.opentoutatice.sharing.operation;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
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
        SharingRunner runner = new EnableSharingRunner(this.session, document);
        runner.silentRun(false);
    }

}
