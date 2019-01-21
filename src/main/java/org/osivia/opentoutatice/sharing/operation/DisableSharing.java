package org.osivia.opentoutatice.sharing.operation;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;

@Operation(id = DisableSharing.ID, category = Constants.CAT_DOCUMENT, label = "Disable sharing")
public class DisableSharing {

    /** Operation identifier. */
    public static final String ID = "Document.DisableSharing";


    /** Core session. */
    @Context
    private CoreSession session;


    /**
     * Constructor.
     */
    public DisableSharing() {
        super();
    }


    /**
     * Run operation.
     * 
     * @param document document
     */
    @OperationMethod
    public void run(DocumentModel document) {
        SharingRunner runner = new DisableSharingRunner(this.session, document);
        runner.silentRun(false);
    }

}
