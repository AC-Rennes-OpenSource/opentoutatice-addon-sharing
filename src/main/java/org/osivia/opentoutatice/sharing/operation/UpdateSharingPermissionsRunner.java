package org.osivia.opentoutatice.sharing.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.nuxeo.ecm.automation.core.util.DocumentHelper;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentRef;
import org.nuxeo.ecm.core.api.model.Property;
import org.nuxeo.ecm.core.api.model.impl.ListProperty;
import org.nuxeo.ecm.core.api.security.ACE;
import org.nuxeo.ecm.core.api.security.ACL;
import org.nuxeo.ecm.core.api.security.ACP;
import org.osivia.opentoutatice.sharing.SharingConstants;

/**
 * Update sharing permissions runner
 * 
 * @author Cédric Krommenhoek
 */
public class UpdateSharingPermissionsRunner extends SharingRunner {

    /** Sharing permission. */
    private final String permission;
    /** User. */
    private final String user;
    /** Add or remove permissions indicator. */
    private final Boolean add;


    /**
     * Constructor.
     * 
     * @param session core sessions
     * @param document document
     * @param permission sharing permission
     * @param user user
     * @param add add or remove permissions indicator
     */
    public UpdateSharingPermissionsRunner(CoreSession session, DocumentModel document, String permission, String user, Boolean add) {
        super(session, document);
        this.permission = permission;
        this.user = user;
        this.add = add;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void run() throws ClientException {
        // Document reference
        DocumentRef ref = this.document.getRef();
        // Access control policy
        ACP acp = this.session.getACP(ref);
        // Sharing access control list
        ACL acl = acp.getOrCreateACL(SharingConstants.ACL);

        if (StringUtils.isEmpty(this.user) && StringUtils.isEmpty(this.permission) && BooleanUtils.isFalse(this.add)) {
            // Remove sharing access control list
            acp.removeACL(SharingConstants.ACL);
        } else if (StringUtils.isEmpty(this.user) && StringUtils.isNotEmpty(this.permission) && (this.add == null)) {
            // Update sharing access control list
            if (CollectionUtils.isNotEmpty(acl)) {
                // Users
                Set<String> users = new HashSet<>();
                for (ACE ace : acl.getACEs()) {
                    users.add(ace.getUsername());
                }

                // Access control entries
                List<ACE> aces = new ArrayList<>(users.size());
                for (String user : users) {
                    ACE ace = new ACE(user, this.permission);
                    aces.add(ace);
                }

                acl.setACEs(aces.toArray(new ACE[aces.size()]));
            }

            // Update document
            try {
                DocumentHelper.setProperty(this.session, this.document, SharingConstants.SHARING_LINK_PERMISSION_XPATH, this.permission);
            } catch (IOException e) {
                throw new ClientException(e);
            }
        } else if (StringUtils.isNotEmpty(this.user) && BooleanUtils.isFalse(this.add)) {
            // Remove user permission
            this.removeUserAce(acl, this.user);

            if (!this.isBannedUser()) {
                // Add user to banned users
                ListProperty bannedUsers = (ListProperty) this.document.getProperty(SharingConstants.SHARING_BANNED_USERS_XPATH);
                bannedUsers.addValue(this.user);
                this.document.setProperty(SharingConstants.SCHEMA, SharingConstants.SHARING_BANNED_USERS_NAME, bannedUsers);
            }
        } else if (StringUtils.isNotEmpty(this.user) && BooleanUtils.isTrue(this.add)) {
            if (!this.isBannedUser()) {
                // Get current permission
                String permission = (String) this.document.getPropertyValue(SharingConstants.SHARING_LINK_PERMISSION_XPATH);

                // Add or update user permission
                this.removeUserAce(acl, this.user);
                ACE ace = new ACE(this.user, permission);
                acl.add(ace);
            }
        } else if (StringUtils.isNotEmpty(this.user) && (this.add == null)) {
            if (this.isBannedUser()) {
                // Banned user property
                Property bannedUser = null;
                ListProperty bannedUsers = (ListProperty) this.document.getProperty(SharingConstants.SHARING_BANNED_USERS_XPATH);
                Iterator<Property> iterator = bannedUsers.iterator();
                while (iterator.hasNext() && (bannedUser == null)) {
                    Property property = iterator.next();
                    String value = (String) property.getValue();
                    if (StringUtils.equals(this.user, value)) {
                        bannedUser = property;
                    }
                }

                if (bannedUser != null) {
                    // Get current permission
                    String permission = (String) this.document.getPropertyValue(SharingConstants.SHARING_LINK_PERMISSION_XPATH);
                    
                    // Add user permission
                    this.removeUserAce(acl, this.user);
                    ACE ace = new ACE(this.user, permission);
                    acl.add(ace);

                    // Remove user from banned users
                    bannedUsers.remove(bannedUser);
                    this.document.setProperty(SharingConstants.SCHEMA, SharingConstants.SHARING_BANNED_USERS_NAME, bannedUsers);
                }
            }
        } else {
            // Unknown case
            StringBuilder message = new StringBuilder();
            message.append("Invalid parameters: permission='");
            message.append(this.permission);
            message.append("' ; user='");
            message.append(this.user);
            message.append("' ; add='");
            message.append(this.add);
            message.append("'.");
            throw new ClientException(message.toString());
        }

        // Update access control policy
        this.session.setACP(ref, acp, true);

        this.session.saveDocument(this.document);
    }


    /**
     * Remove user access control entry.
     * 
     * @param acl access control list
     * @param user user name
     */
    private void removeUserAce(ACL acl, String user) {
        // User access control entry
        ACE ace = this.getUserAce(acl, user);

        if (ace != null) {
            acl.remove(ace);
        }
    }


    /**
     * Get user access control entry.
     * 
     * @param acl access control list
     * @param user user name
     * @return access control entry
     */
    private ACE getUserAce(ACL acl, String user) {
        ACE result = null;

        if (CollectionUtils.isNotEmpty(acl)) {
            Iterator<ACE> iterator = acl.iterator();
            while (iterator.hasNext() && (result == null)) {
                ACE ace = iterator.next();
                if (StringUtils.equals(user, ace.getUsername())) {
                    result = ace;
                }
            }
        }

        return result;
    }


    /**
     * Check if user is banned.
     * 
     * @return true if user is banned
     */
    private boolean isBannedUser() {
        List<?> bannedUsers = (List<?>) this.document.getPropertyValue(SharingConstants.SHARING_BANNED_USERS_XPATH);
        return CollectionUtils.isNotEmpty(bannedUsers) && bannedUsers.contains(this.user);
    }

}
