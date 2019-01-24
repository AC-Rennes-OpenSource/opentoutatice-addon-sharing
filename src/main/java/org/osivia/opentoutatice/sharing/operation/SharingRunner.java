package org.osivia.opentoutatice.sharing.operation;

import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.UnrestrictedSessionRunner;

import fr.toutatice.ecm.platform.core.helper.ToutaticeSilentProcessRunnerHelper;

/**
 * Sharing runner abstract super-class.
 * 
 * @author Cédric Krommenhoek
 * @see UnrestrictedSessionRunner
 */
public abstract class SharingRunner extends ToutaticeSilentProcessRunnerHelper {

    /** Document. */
    protected final DocumentModel document;


    /**
     * Constructor.
     * 
     * @param session core session
     */
    public SharingRunner(CoreSession session, DocumentModel document) {
        super(session);
        this.document = document;
    }

}
