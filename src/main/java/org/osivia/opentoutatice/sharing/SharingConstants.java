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
    /** Sharing schema. */
    public static final String SCHEMA = "sharing";


    /** Sharing author property name. */
    public static final String SHARING_AUTHOR_NAME = "author";
    /** Sharing author XPath. */
    public static final String SHARING_AUTHOR_XPATH = SCHEMA + ":" + SHARING_AUTHOR_NAME;

    /** Sharing link identifier property name. */
    public static final String SHARING_LINK_ID_NAME = "linkId";
    /** Sharing link identifier XPath. */
    public static final String SHARING_LINK_ID_XPATH = SCHEMA + ":" + SHARING_LINK_ID_NAME;

    /** Sharing link permission property name. */
    public static final String SHARING_LINK_PERMISSION_NAME = "linkPermission";
    /** Sharing link permission XPath. */
    public static final String SHARING_LINK_PERMISSION_XPATH = SCHEMA + ":" + SHARING_LINK_PERMISSION_NAME;

    /** Sharing banned users property name. */
    public static final String SHARING_BANNED_USERS_NAME = "bannedUsers";
    /** Sharing banned users XPath. */
    public static final String SHARING_BANNED_USERS_XPATH = SCHEMA + ":" + SHARING_BANNED_USERS_NAME;

    /** Denormalized sharing users XPath. */
    public static final String SHARING_USERS_DENORMALIZED_PROPERTY = SCHEMA + ":users";


    /**
     * Constructor.
     */
    private SharingConstants() {
        super();
    }

}
