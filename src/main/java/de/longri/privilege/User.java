package de.longri.privilege;

import de.longri.DbEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@Component
public class User implements iUserPrivileges, DbEntity {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(User.class);

    public static List<User> USER_LIST;


    public static void add(User user) {
        if (USER_LIST == null) {
            USER_LIST = new UniqueArrayList<>();
        }
        USER_LIST.add(user);
    }

    public static User getFromId(int userId) {
        if (USER_LIST != null) {
            for (User user : USER_LIST) {
                if (user.getId() == userId) {
                    return user;
                }
            }
        }
        return null;
    }

    public static final User SYSTEM_USER = new User(-1, "SYSTEM");

    protected int ID;
    protected String NAME;
    private String first_name;
    private String last_name;
    private String email;
    private int companyId;
    private int departmentId;
    private String privstring;

    public User() {
        this(-2, "");
    }

    /**
     * The PRIVILEGE_LIST variable represents a list of privileges associated with a user in a system.
     * <p>
     * It is an ArrayList of PRIVILEGE objects.
     * <p>
     * This variable is declared in the User class, which represents a user in the system and implements the iUserPrivileges interface.
     * The PRIVILEGE_LIST variable is initialized as an empty ArrayList in the User class constructor.
     * <p>
     * The User class provides methods to manipulate the PRIVILEGE_LIST variable, such as adding or removing privileges from the list.
     *
     * @see User
     * @see iUserPrivileges
     * @see PRIVILEGE
     */
    protected UniqueArrayList<USER_PRIVILEGE> PRIVILEGE_LIST = new UniqueArrayList<>();

    /**
     * The GROUP_LIST variable represents a list of groups in the system.
     * <p>
     * It is an ArrayList of GROUP objects.
     * <p>
     * This variable is declared in the User class, which represents a user in the system and implements the iUserPrivileges interface.
     * The GROUP_LIST variable is initialized as an empty ArrayList in the User class constructor.
     * <p>
     * The User class provides methods to manipulate the GROUP_LIST variable, such as adding or removing groups from the list.
     *
     * @see User
     * @see PRIVILEGE_GROUPS
     */
    protected UniqueArrayList<PRIVILEGE_GROUPS> PRIVILEGEGROUPS_LIST = new UniqueArrayList<>();

    /**
     * Creates a new User with the given ID and name.
     *
     * @param id   the ID of the user
     * @param name the name of the user
     */
    public User(int id, String name) {
        ID = id;
        NAME = name;
    }

    /**
     * Creates a new User with the given ID and name, and optionally adds one or more privileges to the user's list of privileges.
     * <p>
     * The User class represents a user in a system and implements the iUserPrivileges interface to provide functionality for managing user privileges and groups.
     * <p>
     * The User constructor initializes the ID and NAME variables of the User object with the specified values. It also calls the addPrivilege() method to add any privileges passed
     * as arguments.
     *
     * @param id         the ID of the user
     * @param name       the name of the user
     * @param privileges one or more PRIVILEGE objects to be added to the user's list of privileges
     * @see User
     * @see iUserPrivileges
     * @see PRIVILEGE
     */
    public User(int id, String name, PRIVILEGE... privileges) {
        ID = id;
        NAME = name;
        addPrivilege(privileges);
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
        NAME = rs.getString("user_name");
        this.first_name = rs.getString("first_name");
        this.last_name = rs.getString("last_name");
        this.email = rs.getString("email");
        this.companyId = rs.getInt("company_Id");
        this.departmentId = rs.getInt("department_Id");

        privstring = rs.getString("permissions");

    }


    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public String getName() {
        return first_name + " " + last_name;
    }

    public void setFirstName(String name) {
        this.first_name = name;
    }

    public void setLastName(String name) {
        this.last_name = name;
    }

    public String getUserName() {
        return this.NAME;
    }

    public void setUserName(String userName) {
        this.NAME = userName;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getFirstName() {
        return first_name == null ? "" : first_name;
    }

    public String getLastName() {
        return last_name == null ? "" : last_name;
    }

    public User cpy() {
        User user = new User();
        user.ID = ID;
        user.NAME = NAME;
        user.first_name = this.first_name;
        user.last_name = this.last_name;
        user.email = this.email;
        user.companyId = this.companyId;
        user.departmentId = this.departmentId;
        user.PRIVILEGEGROUPS_LIST.addAll(PRIVILEGEGROUPS_LIST);
        user.PRIVILEGE_LIST.addAll(PRIVILEGE_LIST);
        user.privstring = privstring;
        return user;
    }

    public String getPrivilegesJson() {

        if (privstring != null) {
            return privstring;
        }


        try {
            JSONObject json = new JSONObject();

            JSONArray groupsJsn = new JSONArray();
            for (PRIVILEGE_GROUPS group : PRIVILEGEGROUPS_LIST) {
                groupsJsn.put(group.getName());
            }

            JSONArray privileges = extracted(PRIVILEGE_LIST);
            json.put("privileges", privileges);
            json.put("groups", groupsJsn);
            return json.toString();
        } catch (JSONException e) {
            log.error("Error while creating json string", e);
        }
        return null;
    }

    public static String getPrivilegesJson(UniqueArrayList<USER_PRIVILEGE> list) {
        try {
            JSONArray privileges = extracted(list);
            JSONObject json = new JSONObject();
            json.put("privileges", privileges);
            return json.toString();
        } catch (JSONException e) {
            log.error("Error while creating json string", e);
        }
        return null;
    }

    private static JSONArray extracted(UniqueArrayList<USER_PRIVILEGE> list) throws JSONException {
        JSONArray privileges = new JSONArray();
        for (PRIVILEGE privilege : list) {

            if (privilege instanceof USER_PRIVILEGE userPrivilege) {
                JSONObject obj = new JSONObject();
                obj.put(userPrivilege.getName(), userPrivilege.getStatus());
                privileges.put(obj);
            } else {
                privileges.put(privilege.getName());
            }
        }
        return privileges;
    }

    /**
     * Returns the list of privileges associated with this user.
     * <p>
     * This method retrieves the list of privileges associated with the user and returns it as an ArrayList of PRIVILEGE objects.
     *
     * @return The list of privileges associated with this user.
     */
    @Override
    public UniqueArrayList<USER_PRIVILEGE> getPrivilegeList() {
        return PRIVILEGE_LIST;
    }

    /**
     * Returns the list of groups associated with this user.
     * <p>
     * This method retrieves the list of groups associated with the user and returns it as an ArrayList of GROUP objects.
     *
     * @return The list of groups associated with this user.
     */
    @Override
    public UniqueArrayList<PRIVILEGE_GROUPS> getGroupList() {
        return PRIVILEGEGROUPS_LIST;
    }

    public boolean hasPrivilege(PRIVILEGE_GROUPS... groups) {
        return false;
    }

    public USER_PRIVILEGE getUserPrivilege(PRIVILEGE privilege) {
        for (USER_PRIVILEGE userPrivilege : PRIVILEGE_LIST) {
            if (userPrivilege.equals(privilege)) return userPrivilege;
        }
        return null;
    }

    public void clearPrivileges() {
        PRIVILEGE_LIST.clear();
    }

    public void addPrivilege(UniqueArrayList<USER_PRIVILEGE> privileges) {
        for (USER_PRIVILEGE privilege : privileges) {
            addPrivilege(privilege);
        }
    }

    public void initialPrivilege() {
        if (this.privstring != null) {
            setUserPrivilegesFromJsonString(this, privstring);
            privstring = null;
        }
    }


    public final static void setUserPrivilegesFromJsonString(User user, String jsonString) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonString);
            // search for a single permission
            //check if jsonObject contains "permissions"
            if (jsonObj.has("privileges")) {
                JSONArray array = jsonObj.getJSONArray("privileges");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);

                    Iterator<String> keys = obj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();      // Name, z.B. "lizenz.view"
                        String statusString = obj.getString(key); // Wert, z.B. "NONE"

                        PRIVILEGE privilege = Privileges.getPrivilege(key);
                        PRIVILEGE_STATUS status = PRIVILEGE_STATUS.valueOf(statusString);

                        if (privilege != null) {
                            user.addPrivilege(new USER_PRIVILEGE(privilege, status));
                        }
                    }
                }

            }
            if (jsonObj.has("groups")) {
                JSONArray array = jsonObj.getJSONArray("groups");
                for (int i = 0; i < array.length(); i++) {
                    String name = array.get(i).toString();
                    PRIVILEGE_GROUPS group = Privileges.getPrivilegeGroup(name);
                    user.addGroup(group);
                }
            }
        } catch (JSONException e) {
            log.error("Error while setting user privileges from json string", e);
        }
    }
}
