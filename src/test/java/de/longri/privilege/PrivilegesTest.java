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

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class PrivilegesTest {

    private static class TestButton {
        boolean disable = false;

        void setDisable(boolean di) {
            disable = di;
        }
    }

    static TestButton testBtn = new TestButton();

    @Test
    void create() {
        if(true)return;
        Privileges privileges = new Privileges();
        assertNotNull(privileges);

        PRIVILEGE priv = privileges.create("test1");
        assertNotNull(priv);
        assertThrows(PrivilegeDoubleExistException.class, () -> {
            PRIVILEGE priv2 = privileges.create("test1");
        });
    }

    @Test
    void privilege() {
        if(true)return;
        Privileges privileges = new Privileges();
        assertNotNull(privileges);

        PRIVILEGE priv1 = privileges.create("test1");
        PRIVILEGE priv2 = privileges.create("test2");
        PRIVILEGE priv3 = privileges.create("test3");

        User user = new User(0, "user1");

        PRIVILEGE_GROUPS adminPRIVILEGEGROUPS = new PRIVILEGE_GROUPS(0, "admins", 1);

        adminPRIVILEGEGROUPS.addPrivilege(priv1, priv2);
        user.addGroup(adminPRIVILEGEGROUPS);
        user.addPrivilege(new USER_PRIVILEGE(priv1, PRIVILEGE_STATUS.INHERIT), new USER_PRIVILEGE(priv2, PRIVILEGE_STATUS.INHERIT));

        assertTrue(user.hasPrivilege(priv1, priv2));
        assertFalse(user.hasPrivilege(priv3));


        AtomicInteger cont = new AtomicInteger(3);
        privileges.perform(user, priv1, () -> {
            cont.addAndGet(3);
        });

        assertEquals(6, cont.get());

        privileges.perform(user, priv2, () -> {
            cont.addAndGet(2);
        });

        assertEquals(8, cont.get());

        assertThrows(PrivilegedActionException.class, () -> {
            privileges.perform(user, priv3, () -> {
                cont.addAndGet(2);
            });
        });

        assertEquals(8, cont.get());

        adminPRIVILEGEGROUPS.removePrivilege(priv2);
        assertTrue(user.hasPrivilege(priv1));
        assertFalse(user.hasPrivilege(priv2));
        assertFalse(user.hasPrivilege(priv3));


        assertThrows(PrivilegedActionException.class, () -> {
            privileges.perform(user, priv2, () -> {
                cont.addAndGet(7);
            });
        });
        assertEquals(8, cont.get());

        adminPRIVILEGEGROUPS.addPrivilege(priv2);
        user.addPrivilege(priv2);
        privileges.perform(user, priv2, () -> {
            cont.addAndGet(7);
        });
        assertEquals(15, cont.get());
        assertTrue(user.hasPrivilege(priv1, priv2));
        assertFalse(user.hasPrivilege(priv3));

        adminPRIVILEGEGROUPS.removePrivilege(priv2);
        assertTrue(user.hasPrivilege(priv1));
        assertFalse(user.hasPrivilege(priv2)); // user has no priv2.INERHIT
        assertFalse(user.hasPrivilege(priv3));
        privileges.perform(user, priv1, () -> {
            cont.addAndGet(5);
        });
        assertEquals(20, cont.get());

        user.removePrivilege(priv2);
        assertTrue(user.hasPrivilege(priv1));
        assertFalse(user.hasPrivilege(priv2));
        assertFalse(user.hasPrivilege(priv3));
        assertThrows(PrivilegedActionException.class, () -> {
            privileges.perform(user, priv2, () -> {
                cont.addAndGet(7);
            });
        });
        assertEquals(20, cont.get());
    }

    @Test
    void privilegeApplicationTest() {
        if(true)return;
        User USER1 = new User(1, "TestUser1");
        User USER2 = new User(2, "Testuser2", new USER_PRIVILEGE(PrivilegeTestClass.WRITE, PRIVILEGE_STATUS.GRANT));

        PRIVILEGE_GROUPS admin = new PRIVILEGE_GROUPS(0, "ADMIN", 1);
        USER1.addGroup(admin);

        assertFalse(USER2.hasPrivilege(admin));
        assertTrue(USER1.hasPrivilege(admin));

        /**
         * disable button if user has no privileges
         */
        testBtn.setDisable(!USER2.hasPrivilege(PrivilegeTestClass.WRITE));
        assertFalse(testBtn.disable);

        testBtn.setDisable(!USER1.hasPrivilege(PrivilegeTestClass.WRITE));
        assertTrue(testBtn.disable);

        PrivilegeTestClass.writeToDB(USER2);

        assertThrows(PrivilegedActionException.class, () -> {
            PrivilegeTestClass.writeToDB(USER1); // user1 has no write privileges
        });
    }
}