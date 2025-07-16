package de.longri.privilege;


/**
 * The iUserPrivileges interface represents the privileges of a user in a system. It provides methods to get the list of privileges and groups associated with the user, check if the
 * user has a specific privilege or set of privileges, add or remove privileges from the user, and add or remove groups from the user.
 */
public interface iUserPrivileges {

    /**
     * Retrieves the list of privileges associated with this user.
     * <p>
     * This method returns an ListenableArrayList of PRIVILEGE objects representing the privileges of the user.
     *
     * @return The list of privileges associated with this user.
     */
    UniqueArrayList<USER_PRIVILEGE> getPrivilegeList();

    /**
     * Returns the list of groups associated with this user.
     * <p>
     * This method returns an ListenableArrayList of GROUP objects representing the groups of the user.
     *
     * @return The list of groups associated with this user.
     */
    UniqueArrayList<PRIVILEGE_GROUPS> getGroupList();


    /**
     * Checks if the user has the specified privileges associated with the given groups.
     *
     * @param groups The groups to check for privilege.
     * @return {@code true} if the user has one or more privileges associated with any of the given groups, {@code false} otherwise.
     */
    default boolean hasPrivilege(PRIVILEGE_GROUPS... groups) {
        for (PRIVILEGE_GROUPS gr : groups) {
            if (hasPrivilege(gr, getGroupList())) return true;
        }
        return false;
    }

    default boolean hasPrivilege(PRIVILEGE_GROUPS group, UniqueArrayList<PRIVILEGE_GROUPS> groups) {
        for (PRIVILEGE_GROUPS g : groups) {
            if (g == group) return true;
            if (hasPrivilege(group, g.PRIVILEGEGROUPS_CHILD_LIST)) return true;
        }
        return false;
    }

    /**
     * Checks if the user has the specified privilege.
     * <p>
     * This method iterates over the user's list of privileges and groups to determine if the user has the specified privilege. It returns {@code true} if the user has the privilege
     * and {@code false} otherwise.
     *
     * @param privilege The privilege to check for.
     * @return {@code true} if the user has the specified privilege, {@code false} otherwise.
     */
    default boolean hasPrivilege(PRIVILEGE privilege) {

        UniqueArrayList<USER_PRIVILEGE> list = getPrivilegeList();

        for (PRIVILEGE pri : list)
            if (pri.ID == privilege.ID) {
                //check status
                if (pri instanceof USER_PRIVILEGE userPrivilege) {
                    if (userPrivilege.getStatus() == PRIVILEGE_STATUS.INHERIT) {
                        // check if user in any Group with this Privilege
                        return hasPrivilege(privilege, getGroupList());
                    } else if (userPrivilege.getStatus() == PRIVILEGE_STATUS.DENY) {
                        return false;
                    } else if (userPrivilege.getStatus() == PRIVILEGE_STATUS.GRANT) {
                        return true;
                    }
                } else {
                    return true;
                }
            }


        // check if privilege over any group
        if (hasPrivilege(privilege, getGroupList())) return true;

        return false;
    }

    default boolean hasPrivilege(PRIVILEGE privilege, UniqueArrayList<PRIVILEGE_GROUPS> groups) {
        for (PRIVILEGE_GROUPS g : groups) {
            for (PRIVILEGE pri : g.PRIVILEGE_LIST)
                if (pri == privilege) return true;

            if (hasPrivilege(privilege, g.PRIVILEGEGROUPS_CHILD_LIST)) return true;
        }
        return false;
    }

    /**
     * Checks if the user has the specified privilege.
     * <p>
     * This method iterates over the user's list of privileges and groups to determine if
     * the user has the specified privilege. It returns true if the user has the privilege
     * and false otherwise.
     *
     * @param privilege The privilege to check for.
     * @return true if the user has the specified privilege, false otherwise.
     */
    default boolean hasExplicitPrivilege(PRIVILEGE privilege) {
        for (PRIVILEGE pri : getPrivilegeList())
            if (pri.ID == privilege.ID) return true;
        return false;
    }

    /**
     * Checks if the user has the specified privileges.
     * <p>
     * This method iterates over the user's list of privileges and groups to determine if the user has all of the specified privileges.
     * It returns {@code true} if the user has all the specified privileges, and {@code false} otherwise.
     *
     * @param privileges The privileges to check for.
     * @return {@code true} if the user has all the specified privileges, {@code false} otherwise.
     */
    default boolean hasPrivilege(PRIVILEGE... privileges) {
        if (privileges == null) return false;
        for (PRIVILEGE privilege : privileges)
            if (!hasPrivilege(privilege)) return false;
        return true;
    }

    /**
     * Checks if the user has any of the specified privileges.
     * This method iterates over the provided privileges and determines if the user has at least one of them.
     *
     * @param privileges The privileges to check for. This can include one or more PRIVILEGE objects.
     *                   If {@code null}, the method will return {@code false}.
     * @return {@code true} if the user has at least one of the specified privileges, {@code false} otherwise.
     */
    default boolean hasAnyPrivilege(PRIVILEGE... privileges) {
        if (privileges == null) return false;
        for (PRIVILEGE privilege : privileges)
            if (hasPrivilege(privilege)) return true;
        return false;
    }

        /**
         * Adds a group to the user's list of groups.
         * <p>
         * This method adds the specified group to the list of groups associated with the user.
         * The group is added to the user's list of groups.
         *
         * @param PRIVILEGEGROUPS The group to be added.
         */
    default void addGroup(PRIVILEGE_GROUPS PRIVILEGEGROUPS) {
        if (PRIVILEGEGROUPS == null) return;
        getGroupList().add(PRIVILEGEGROUPS);
    }


    default void removeGroup(PRIVILEGE_GROUPS PRIVILEGEGROUPS) {
        if (PRIVILEGEGROUPS == null) return;
        getGroupList().remove(PRIVILEGEGROUPS);
    }


    /**
     * Removes the specified privileges from the list of privileges associated with this user.
     *
     * @param privileges The privileges to be removed.
     */
    default void removePrivilege(PRIVILEGE... privileges) {
        if (privileges == null) return;
        for (PRIVILEGE privilege : privileges)
            getPrivilegeList().remove(privilege);
    }

    /**
     * Adds one or more privileges to the user's list of privileges.
     * <p>
     * This method adds the specified privileges to the list of privileges associated with the user.
     *
     * @param privileges One or more PRIVILEGE objects to be added to the user's list of privileges.
     */
    default void addPrivilege(PRIVILEGE... privileges) {
        if (privileges == null) return;
        for (PRIVILEGE privilege : privileges)
            if (privilege instanceof USER_PRIVILEGE userPrivilege)
                getPrivilegeList().add(userPrivilege);
            else
                getPrivilegeList().add(new USER_PRIVILEGE(privilege));
    }
}
