package org.osivia.opentoutatice.sharing;

/**
 * Sharing constants.
 * 
 * @author Cédric Krommenhoek
 */
public class SharingConstants {

    /** Sharing facet. */
    public static final String FACET = "Sharing";
    /** Sharing ACL. */
    public static final String ACL = "sharing";

    /** Sharing link identifier Nuxeo document property. */
    public static final String SHARING_LINK_ID_PROPERTY = "sharing:linkId";
    /** Sharing link permission Nuxeo document property. */
    public static final String SHARING_LINK_PERMISSION_PROPERTY = "sharing:linkPermission";

    /** Sharing users Nuxeo document denormalized property. */
    public static final String SHARING_USERS_DENORMALIZED_PROPERTY = "sharing:users";


    /**
     * Constructor.
     */
    private SharingConstants() {
        super();
    }

}
