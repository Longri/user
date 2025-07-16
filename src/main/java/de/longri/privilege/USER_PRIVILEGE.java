package de.longri.privilege;

/**
 * The USER_PRIVILEGE class extends the PRIVILEGE class to add user-specific attributes and behaviors related to privilege operations.
 * <p>
 * It introduces the STATUS field to represent the current status of the privilege for a user.
 * The class provides methods to retrieve and update the status.
 */
public class USER_PRIVILEGE extends PRIVILEGE {

    private PRIVILEGE_STATUS STATUS;

    /**
     * Constructs a USER_PRIVILEGE object with the specified privilege.
     * <p>
     * This constructor initializes a USER_PRIVILEGE instance by invoking the superclass constructor
     * with details from the provided PRIVILEGE object, such as the ID, name, and whether a reason comment
     * is required. It also initializes the STATUS field to PRIVILEGE_STATUS.NONE.
     *
     * @param privilege The PRIVILEGE object that provides the ID, name, and other attributes for the USER_PRIVILEGE instance.
     */
    public USER_PRIVILEGE(PRIVILEGE privilege) {
        super(privilege.ID, privilege.NAME, privilege.NEED_A_REASON_COMMENT);
        STATUS = PRIVILEGE_STATUS.NONE;
    }

    public USER_PRIVILEGE(PRIVILEGE privilege, PRIVILEGE_STATUS status) {
        super(privilege.ID, privilege.NAME, privilege.NEED_A_REASON_COMMENT);
        STATUS = status;
    }

    /**
     * Retrieves the current status of the privilege.
     *
     * @return The current privilege status, represented as a value of the PRIVILEGE_STATUS enum.
     */
    public PRIVILEGE_STATUS getStatus() {
        return STATUS;
    }

    /**
     * Updates the current status of the privilege.
     *
     * @param status The new privilege status to be set. This must be one of the values from the PRIVILEGE_STATUS enum,
     *               such as GRANT, DENY, INHERIT, or NONE.
     */
    public void setStatus(PRIVILEGE_STATUS status) {
        STATUS = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof USER_PRIVILEGE) {
            USER_PRIVILEGE other = (USER_PRIVILEGE) obj;
            return super.equals(other); // ignore status for easily add and remove from ArrayList { && STATUS == other.STATUS;
        } else if (obj instanceof PRIVILEGE privilege) {
            if (this.ID == privilege.ID && this.NAME.equals(privilege.NAME))
                return true;
        }
        return false;
    }

}
