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

import de.longri.DbEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The GROUP class represents a group of users with certain privileges.
 * It contains information about the group ID, name, and the list of privileges.
 * A group can contain subgroups. Subgroups then inherit the privileges of the parent group.
 */
@Component
public class PRIVILEGE_GROUPS implements DbEntity {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PRIVILEGE_GROUPS.class);

    int ID;
    String Name;
    String comment;
    int creatorUserId;
    LocalDateTime creation_date;
    final UniqueArrayList<PRIVILEGE> PRIVILEGE_LIST = new UniqueArrayList<>();
    final UniqueArrayList<PRIVILEGE_GROUPS> PRIVILEGEGROUPS_CHILD_LIST = new UniqueArrayList<>();
    PRIVILEGE_GROUPS parentPRIVILEGEGROUPS;

    public PRIVILEGE_GROUPS() {
        initListener();
    }


    /**
     * Represents a group of users with certain privileges.
     * <p>
     * This class contains information about the group's ID and name.
     * It also manages the list of privileges and users associated with the group.
     *
     * @param id   The ID of the group.
     * @param name The name of the group.
     */
    public PRIVILEGE_GROUPS(int id, String name, int creatorUserId) {
        ID = id;
        Name = name;
        this.creatorUserId = creatorUserId;
        creation_date = LocalDateTime.now();
        initListener();
    }

    public PRIVILEGE_GROUPS(int id, String name, PRIVILEGE... privileges) {
        ID = id;
        Name = name;
        this.creatorUserId = -1;
        creation_date = LocalDateTime.now();
        addPrivilege(privileges);
        initListener();
    }

    public PRIVILEGE_GROUPS(int id, String name, PRIVILEGE_GROUPS... groups) {
        ID = id;
        Name = name;
        this.creatorUserId = -1;
        creation_date = LocalDateTime.now();
        addGroups(groups);
        initListener();
    }

    public PRIVILEGE_GROUPS(ResultSet rs,JdbcTemplate jdbcTemplate, User executeUser) throws SQLException {
        initFromResultSet(rs, jdbcTemplate, executeUser);
        initListener();
    }


    private void initListener() {
        PRIVILEGE_LIST.addChangeListener(new UniqueArrayList.ChangeListener() {
            @Override
            public void listChanged() {
                log.debug("PRIVILEGE_LIST changed {}", PRIVILEGE_LIST.toString());
            }
        });
    }


    /**
     * @param rs
     * @param jdbcTemplate
     * @param executeUser
     * @throws SQLException
     */
    @Override
    public void initFromResultSet(ResultSet rs, JdbcTemplate jdbcTemplate, User executeUser) throws SQLException {
        ID = rs.getInt("id");
        Name = rs.getString("name");
        creatorUserId = rs.getInt("creator_user_id");
        creation_date = rs.getTimestamp("creation_date").toLocalDateTime();
        comment = rs.getString("comment");
    }

    public int getId() {
        return ID;
    }


    /**
     * Adds one or more privileges to the group.
     * <p>
     * This method adds the specified privileges to the group's PRIVILEGE_LIST.
     *
     * @param privileges The privileges to be added to the group.
     * @return The group object itself.
     */
    public PRIVILEGE_GROUPS addPrivilege(PRIVILEGE... privileges) {
        for (PRIVILEGE priv : privileges) {
            if (!PRIVILEGE_LIST.contains(priv))
                PRIVILEGE_LIST.add(priv);
        }
        return this;
    }

    public PRIVILEGE_GROUPS addGroups(PRIVILEGE_GROUPS... groups) {
        for (PRIVILEGE_GROUPS group : groups) {
            if (!PRIVILEGEGROUPS_CHILD_LIST.contains(group))
                PRIVILEGEGROUPS_CHILD_LIST.add(group);
        }
        return this;
    }

    private void setParent(PRIVILEGE_GROUPS PRIVILEGEGROUPS) {
        parentPRIVILEGEGROUPS = PRIVILEGEGROUPS;
    }

    public void removeGroup(PRIVILEGE_GROUPS PRIVILEGEGROUPS) {
        if (PRIVILEGEGROUPS_CHILD_LIST.remove(PRIVILEGEGROUPS))
            PRIVILEGEGROUPS.setParent(null);
    }


    /**
     * Removes the specified privileges from the group's PRIVILEGE_LIST.
     * This method removes each privilege from the list of privileges in the group.
     *
     * @param privileges The privileges to be removed from the group.
     */
    public void removePrivilege(PRIVILEGE... privileges) {
        for (PRIVILEGE priv : privileges)
            PRIVILEGE_LIST.remove(priv);
    }

    @Override
    public String toString() {
        return "GROUP: " + Name;
    }

    public UniqueArrayList<PRIVILEGE_GROUPS> getGroups() {
        return PRIVILEGEGROUPS_CHILD_LIST;
    }

    public String getName() {
        if (Name == null) return "";
        return Name;
    }

    public UniqueArrayList<PRIVILEGE> getPrivieleges() {
        return PRIVILEGE_LIST;
    }

    public PRIVILEGE_GROUPS getParrent() {
        return parentPRIVILEGEGROUPS;
    }

    public int getCreatedByUserID() {
        return creatorUserId;
    }

    public LocalDateTime getCreatedDateTime() {
        return creation_date;
    }

    public PRIVILEGE_GROUPS cpy() {
        PRIVILEGE_GROUPS newGroup = new PRIVILEGE_GROUPS(ID, Name, creatorUserId);
        newGroup.PRIVILEGE_LIST.addAll(PRIVILEGE_LIST);
        newGroup.PRIVILEGEGROUPS_CHILD_LIST.addAll(PRIVILEGEGROUPS_CHILD_LIST);
        newGroup.comment = comment;
        return newGroup;
    }

    public String getComment() {
        if (comment == null) return "";
        return comment;
    }

    public void addPrivilegeChangeListener(UniqueArrayList.ChangeListener listener) {
        PRIVILEGE_LIST.addChangeListener(listener);
    }

    public String getPrivilegesJson() {
        try {
            JSONObject json = new JSONObject();
            JSONArray privileges = new JSONArray();
            for (PRIVILEGE privilege : PRIVILEGE_LIST) {
                privileges.put(privilege.getName());
            }
            json.put("privileges", privileges);
            return json.toString();
        } catch (JSONException e) {
            log.error("Error while creating json string", e);
        }
        return null;
    }

    public void setComment(String testComment) {
        this.comment = testComment;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void clearAllPrivileges() {
        PRIVILEGE_LIST.clear();
    }

}
