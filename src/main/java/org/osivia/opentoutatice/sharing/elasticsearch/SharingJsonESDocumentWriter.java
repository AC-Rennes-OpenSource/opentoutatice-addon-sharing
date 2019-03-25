package org.osivia.opentoutatice.sharing.elasticsearch;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.automation.jaxrs.io.documents.JsonESDocumentWriter;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.security.ACE;
import org.nuxeo.ecm.core.api.security.ACL;
import org.nuxeo.ecm.core.api.security.ACP;
import org.osivia.opentoutatice.sharing.SharingConstants;

import fr.toutatice.ecm.es.customizer.writers.api.AbstractCustomJsonESWriter;

/**
 * Sharing JSON Elasticsearch document writer.
 *
 * @author Cédric Krommenhoek
 * @see JsonESDocumentWriter
 */
public class SharingJsonESDocumentWriter extends AbstractCustomJsonESWriter {

    /**
     * Constructor.
     */
    public SharingJsonESDocumentWriter() {
        super();
    }


    /**
     * Write sharing properties.
     * 
     * @param jsonGenerator JSON generator
     * @param document document
     * @throws IOException
     */
    private void writeSharingProperties(JsonGenerator jsonGenerator, DocumentModel document) throws IOException {
        // Access control policy
        ACP acp = document.getACP();
        // Sharing access control list
        ACL acl = acp.getACL(SharingConstants.ACL);

        // Denormalized sharing users
        jsonGenerator.writeArrayFieldStart(SharingConstants.SHARING_USERS_DENORMALIZED_PROPERTY);
        if (CollectionUtils.isNotEmpty(acl)) {
            for (ACE ace : acl) {
                String user = ace.getUsername();
                if (StringUtils.isNotEmpty(user)) {
                    jsonGenerator.writeString(user);
                }
            }
        }
        jsonGenerator.writeEndArray();
    }


    @Override
    public boolean accept(DocumentModel doc) {
        if (doc.hasFacet(SharingConstants.FACET)) {

            return true;
        }
        else return false;
    }


    @Override
    public void writeData(JsonGenerator jsonGenerator, DocumentModel document, String[] schemas, Map<String, String> contextParameters) throws IOException {

        this.writeSharingProperties(jsonGenerator, document);

    }

}
