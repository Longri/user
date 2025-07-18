package de.longri.privilege;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PRIVILEGE_GROUPSTest {

    @BeforeAll
    static void setUp() {
        System.setProperty("de.longri.privilege.class", "de.longri.privilege.UserPrivileges");
    }


    @Test
    void getPrivilegesJson() {
        if(true)return;
        PRIVILEGE_GROUPS group = new PRIVILEGE_GROUPS();
        group.addPrivilege(UserPrivileges.USER_CREATE, UserPrivileges.USER_DELETE);
        assertEquals("{\"privileges\":[\"user.create\",\"user.delete\"]}", group.getPrivilegesJson());
    }
}