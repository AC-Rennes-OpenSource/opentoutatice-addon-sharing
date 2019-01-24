package org.osivia.opentoutatice.sharing.operation;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;

/**
 * Update sharing permissions operation.
 * 
 * @author Cédric Krommenhoek
 */
@Operation(id = UpdateSharingPermissions.ID, category = Constants.CAT_DOCUMENT, label = "Update sharing permissions")
public class UpdateSharingPermissions {

    /** Operation identifier. */
    public static final String ID = "Document.UpdateSharingPermissions";


    /** Core session. */
    @Context
    private CoreSession session;

    /** Sharing permission. */
    @Param(name = "permission", required = false, description = "Sharing permission")
    private String permission;

    /** User. */
    @Param(name = "user", required = false, description = "User")
    private String user;

    /** Add or remove permissions indicator. */
    @Param(name = "add", required = false, description = "Add or remove permissions indicator")
    private Boolean add;


    /**
     * Constructor.
     */
    public UpdateSharingPermissions() {
        super();
    }


    /**
     * Run operation.
     * 
     * @param document document
     */
    @OperationMethod
    public void run(DocumentModel document) {
        SharingRunner runner = new UpdateSharingPermissionsRunner(this.session, document, this.permission, this.user, this.add);
        runner.silentRun(false);
    }

}
