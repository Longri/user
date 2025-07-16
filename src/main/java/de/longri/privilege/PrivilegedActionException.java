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
 * The PrivilegedActionException class represents an exception that is thrown when a user is not privileged to perform a specific function.
 */
public class PrivilegedActionException extends PrivilegeException {

    /**
     * The PrivilegedActionException class represents an exception that is thrown when a user is not privileged to perform a specific function.
     */
    public PrivilegedActionException(User user) {
        super("User " + user.getName() + " is not privileged to perform this function");
    }
}
