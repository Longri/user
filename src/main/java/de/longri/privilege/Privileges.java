/*
 * Copyright (C) 2024 Longri
 *
 * This file is part of fxutils.
 *
 * fxutils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * fxutils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with fxutils. If not, see <https://www.gnu.org/licenses/>.
 */
package de.longri.privilege;


import java.util.ArrayList;

/**
 * The Privileges class represents a collection of privileges.
 * <p>
 * It provides methods for creating privileges, performing actions with privileges, and checking if a user has a specific privilege.
 * <p>
 * The class contains an inner interface "perform" that represents an action to be performed using a privilege.
 * <p>
 * The class also contains a list of all privileges called "ALL_PRIVILEGE_LIST".
 * <p>
 * The class overrides the toString() method to provide a string representation of the privileges.
 * <p>
 * Example usage:
 * <p>
 * public class PrivilegeTestClass extends Privileges {
 * <p>
 * static final PrivilegeTestClass INSTANCE = new PrivilegeTestClass();
 * <p>
 * public static PRIVILEGE WRITE = INSTANCE.create("write");
 * <p>
 * public static PRIVILEGE READ = INSTANCE.create("read");
 * <p>
 * public static void writeToDB(User user) {
 * INSTANCE.perform(user, WRITE, new perform() {
 *
 * @Override public void run() {
 * //TODO implement your work here
 * }
 * });
 * }
 * <p>
 * }
 * <p>
 * PrivilegeDoubleExistException:
 * <p>
 * The PrivilegeDoubleExistException class represents an exception that is thrown when a duplicate privilege is attempted to be created.
 * <p>
 * The exception message includes the name of the duplicate privilege.
 * <p>
 * PRIVILEGE:
 * <p>
 * The PRIVILEGE class represents a privilege with a unique ID and name.
 * <p>
 * It provides getters for accessing the ID and name of the privilege.
 * It also overrides the toString() method to provide a string representation of the privilege.
 * <p>
 * perform:
 * <p>
 * The perform interface represents an action to be performed using a privilege.
 * It contains a single method "run()" that performs the action.
 * <p>
 * hasPrivilege:
 * <p>
 * Checks if the user has the specified privilege.
 * <p>
 * It iterates over the user's list of privileges and groups to determine if the user has the specified privilege.
 * It returns true if the user has the privilege, and false otherwise.
 * <p>
 * PrivilegedActionException:
 * <p>
 * The PrivilegedActionException class represents an exception that is thrown when a user is not privileged to perform a specific function.
 * <p>
 * User:
 * <p>
 * The User class represents a user with a unique ID and name.
 * <p>
 * It implements the iUserPrivileges interface, which provides methods for accessing the user's privilege and group lists.
 * <p>
 * The class overrides the toString() method to provide a string representation of the user.
 */
public class Privileges {

    final static boolean NEED_A_REASON_COMMENT = true;

    public final static Privileges INSTANCE = new Privileges();



    /**
     * The variable ALL_PRIVILEGE_LIST is an ArrayList of type PRIVILEGE that represents a list of all privileges.
     * <p>
     * This variable is declared in the Privileges class and is initialized as an empty ArrayList.
     * <p>
     * The ALL_PRIVILEGE_LIST variable can be accessed and modified within the Privileges class and its subclasses.
     *
     * @see PRIVILEGE
     */
    private final ArrayList<PRIVILEGE> ALL_PRIVILEGE_LIST = new ArrayList<>();
    private final ArrayList<PRIVILEGE_GROUPS> ALL_PRIVILEGE_GROUPS_LIST = new ArrayList<>();

    /**
     * Creates a new privilege with the given name.
     * <p>
     * This method checks if a privilege with the same name already exists in the list of privileges. If it does, a PrivilegeDoubleExistException is thrown. Otherwise, a new PRIV
     * ILEGE object is created with a unique ID and the given name, and added to the ALL_PRIVILEGE_LIST.
     *
     * @param name The name of the privilege to create.
     * @return The newly created PRIVILEGE object.
     * @throws PrivilegeDoubleExistException If a privilege with the same name already exists.
     */
    public PRIVILEGE create(String name) {
        return create(name, false);
    }

    public PRIVILEGE create(String name, boolean needAReasonComment) {
        // check if exist
        for (PRIVILEGE privilege : ALL_PRIVILEGE_LIST) {
            if (privilege.NAME.equals(name)) {
                throw new PrivilegeDoubleExistException(name);
            }
        }
        PRIVILEGE privilege = new PRIVILEGE(ALL_PRIVILEGE_LIST.size(), name, needAReasonComment);
        ALL_PRIVILEGE_LIST.add(privilege);
        return privilege;
    }


    public PRIVILEGE_GROUPS createGroup(int id, String name, PRIVILEGE... privileges) {

        for (PRIVILEGE_GROUPS group : ALL_PRIVILEGE_GROUPS_LIST) {
            if (group.ID == id) {
                throw new PrivilegeGroupDoubleIdExistException(id);
            }
            if (group.Name.equals(name)) {
                throw new PrivilegeGroupDoubleNameExistException(name);
            }
        }

        PRIVILEGE_GROUPS group = new PRIVILEGE_GROUPS(id, name, privileges);
        ALL_PRIVILEGE_GROUPS_LIST.add(group);
        return group;
    }

    public PRIVILEGE_GROUPS createGroup(int id, String name, PRIVILEGE_GROUPS... groups) {

        for (PRIVILEGE_GROUPS group : ALL_PRIVILEGE_GROUPS_LIST) {
            if (group.ID == id) {
                throw new PrivilegeGroupDoubleIdExistException(id);
            }
            if (group.Name.equals(name)) {
                throw new PrivilegeGroupDoubleNameExistException(name);
            }
        }

        PRIVILEGE_GROUPS group = new PRIVILEGE_GROUPS(id, name, groups);
        ALL_PRIVILEGE_GROUPS_LIST.add(group);
        return group;
    }

    protected PRIVILEGE getPrivilege(String name) {
        for (PRIVILEGE privilege : ALL_PRIVILEGE_LIST) {
            if (privilege.NAME.equals(name)) {
                return privilege;
            }
        }
        return null;
    }

    protected PRIVILEGE_GROUPS getPrivilegeGroup(String name) {
        for (PRIVILEGE_GROUPS group : ALL_PRIVILEGE_GROUPS_LIST) {
            if (group.Name.equals(name)) {
                return group;
            }
        }
        return null;
    }


    /**
     * The perform interface represents a specific action or task that can be performed.
     * <p>
     * Implementing classes must provide a concrete implementation of the run() method, which will be called to perform the designated action.
     */
    public interface perform {
        void run();
    }

    /**
     * Performs a specific action or task based on the provided user, privilege, and operation.
     * <p>
     * If the user does not have the specified privilege, a PrivilegedActionException is thrown.
     * Otherwise, the provided operation is executed.
     *
     * @param user      The user attempting to perform the action.
     * @param privilege The privilege required to perform the action.
     * @param o         The operation to be performed.
     * @throws PrivilegedActionException If the user does not have the specified privilege.
     */
    public void perform(User user, PRIVILEGE privilege, perform o) {
        if (!user.hasPrivilege(privilege))
            throw new PrivilegedActionException(user);
        o.run();
    }

    @Override
    public String toString() {
        return "Privileges: " + ALL_PRIVILEGE_LIST.toString();
    }
}
