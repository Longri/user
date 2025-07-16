package de.longri.privilege;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class USER_PRIVILEGETest {

    @Test
    void setGetStatus_Test() {

        USER_PRIVILEGE up = new USER_PRIVILEGE(UserPrivileges.USER_CREATE);
        assertNotNull(up);
        assertEquals(PRIVILEGE_STATUS.NONE, up.getStatus());
        up.setStatus(PRIVILEGE_STATUS.GRANT);
        assertEquals(PRIVILEGE_STATUS.GRANT, up.getStatus());
        up.setStatus(PRIVILEGE_STATUS.DENY);
        assertEquals(PRIVILEGE_STATUS.DENY, up.getStatus());
        up.setStatus(PRIVILEGE_STATUS.INHERIT);
        assertEquals(PRIVILEGE_STATUS.INHERIT, up.getStatus());
        up.setStatus(PRIVILEGE_STATUS.NONE);
        assertEquals(PRIVILEGE_STATUS.NONE, up.getStatus());
    }

    @Test
    void setGetPrivilege_Test() {
        USER_PRIVILEGE up = new USER_PRIVILEGE(UserPrivileges.USER_CREATE);
        User usr = new User();
        assertNotNull(up);
        assertNotNull(usr);

        String privilegesJson = usr.getPrivilegesJson();
        assertEquals("{\"privileges\":[],\"groups\":[]}", privilegesJson);

        usr.addPrivilege(up);
        // false => up hase status NONE
        assertFalse(usr.hasPrivilege(UserPrivileges.USER_CREATE));
        assertFalse(usr.hasPrivilege(UserPrivileges.USER_DELETE));
        privilegesJson = usr.getPrivilegesJson();
        assertEquals("{\"privileges\":[{\"user.create\":\"NONE\"}],\"groups\":[]}", privilegesJson);


        up.setStatus(PRIVILEGE_STATUS.GRANT);
        assertTrue(usr.hasPrivilege(UserPrivileges.USER_CREATE));
        assertFalse(usr.hasPrivilege(UserPrivileges.USER_DELETE));
        privilegesJson = usr.getPrivilegesJson();
        assertEquals("{\"privileges\":[{\"user.create\":\"GRANT\"}],\"groups\":[]}", privilegesJson);

        up.setStatus(PRIVILEGE_STATUS.DENY);
        assertFalse(usr.hasPrivilege(UserPrivileges.USER_CREATE));
        assertFalse(usr.hasPrivilege(UserPrivileges.USER_DELETE));
        privilegesJson = usr.getPrivilegesJson();
        assertEquals("{\"privileges\":[{\"user.create\":\"DENY\"}],\"groups\":[]}", privilegesJson);

        up.setStatus(PRIVILEGE_STATUS.INHERIT);
        assertFalse(usr.hasPrivilege(UserPrivileges.USER_CREATE));
        assertFalse(usr.hasPrivilege(UserPrivileges.USER_DELETE));
        privilegesJson = usr.getPrivilegesJson();
        assertEquals("{\"privileges\":[{\"user.create\":\"INHERIT\"}],\"groups\":[]}", privilegesJson);

        //create group with privilege and add user

        PRIVILEGE_GROUPS group = new PRIVILEGE_GROUPS(1, "TestGroup",0);
        group.addPrivilege(UserPrivileges.USER_CREATE);
        usr.addGroup(group);
        assertTrue(usr.hasPrivilege(UserPrivileges.USER_CREATE));
        privilegesJson = usr.getPrivilegesJson();
        assertEquals("{\"privileges\":[{\"user.create\":\"INHERIT\"}],\"groups\":[\"TestGroup\"]}", privilegesJson);


    }
}