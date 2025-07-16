package de.longri.privilege;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PRIVILEGE_GROUPSTest {

    @Test
    void getPrivilegesJson() {
        PRIVILEGE_GROUPS group = new PRIVILEGE_GROUPS();
        group.addPrivilege(UserPrivileges.USER_CREATE, UserPrivileges.USER_DELETE);
        assertEquals("{\"privileges\":[\"user.create\",\"user.delete\"]}", group.getPrivilegesJson());
    }
}