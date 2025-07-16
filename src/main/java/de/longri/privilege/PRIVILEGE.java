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

/**
 * The PRIVILEGE class represents a privilege with a unique ID and name.
 * <p>
 * The class provides getters for accessing the ID and name of the privilege.
 * It also overrides the toString() method to provide a string representation of the privilege.
 */
public class PRIVILEGE {

    final int ID;
    final String NAME;
    final boolean NEED_A_REASON_COMMENT;

    /**
     * The PRIVILEGE class represents a privilege with a unique ID and name.
     * <p>
     * The class provides getters for accessing the ID and name of the privilege.
     * It also overrides the toString() method to provide a string representation of the privilege.
     *
     * @param id   The unique ID of the privilege.
     * @param name The name of the privilege.
     */
    PRIVILEGE(int id, String name) {
        NAME = name;
        ID = id;
        NEED_A_REASON_COMMENT = false;
    }

    PRIVILEGE(int id, String name, boolean needAReasonComment) {
        NAME = name;
        ID = id;
        NEED_A_REASON_COMMENT = needAReasonComment;

    }

    @Override
    public String toString() {
        return "PRIVILEGE: " + NAME;
    }

    public String getName() {
        return NAME;
    }


    public boolean needReason() {
        return NEED_A_REASON_COMMENT;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PRIVILEGE other) ) return false;
        return (this.ID == other.ID && this.NAME.equals(other.NAME));
    }
}
