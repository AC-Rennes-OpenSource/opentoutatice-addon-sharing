package org.osivia.opentoutatice.sharing.operation;

import org.apache.commons.collections.CollectionUtils;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;
import org.nuxeo.ecm.core.api.security.ACE;
import org.nuxeo.ecm.core.api.security.ACL;
import org.nuxeo.ecm.core.api.security.ACP;
import org.osivia.opentoutatice.sharing.SharingConstants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Get sharing permissions operation.
 * 
 * @author Cédric Krommenhoek
 */
@Operation(id = GetSharingPermissions.ID, category = Constants.CAT_DOCUMENT, label = "Get sharing permissions")
public class GetSharingPermissions {

    /** Operation identifier. */
    public static final String ID = "Document.GetSharingPermissions";


    /** Operation result MIME type. */
    private static final String MIME_TYPE = "application/json";


    /** Core session. */
    @Context
    private CoreSession session;


    /**
     * Constructor.
     */
    public GetSharingPermissions() {
        super();
    }


    /**
     * Run operation.
     * 
     * @param document document
     * @return operation result
     */
    @OperationMethod
    public Object run(DocumentModel document) {
        // Access control policy
        ACP acp = document.getACP();
        // Sharing access control list
        ACL acl = acp.getACL(SharingConstants.ACL);

        // JSON array
        JSONArray array = new JSONArray();

        if (CollectionUtils.isNotEmpty(acl)) {
            for (ACE ace : acl) {
                // JSON object
                JSONObject object = new JSONObject();
                object.put("user", ace.getUsername());
                object.put("permission", ace.getPermission());

                array.add(object);
            }
        }

        return new StringBlob(array.toString(), MIME_TYPE);
    }

}
