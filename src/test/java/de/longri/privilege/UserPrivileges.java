package de.longri.privilege;


public class UserPrivileges extends Privileges {




    // lizenz
    public final static PRIVILEGE LIZENZ_VIEW = Privileges.create("lizenz.view");
    public final static PRIVILEGE LIZENZ_CREATE = Privileges.create("lizenz.create");
    public final static PRIVILEGE LIZENZ_EDIT = Privileges.create("lizenz.edit");
    public final static PRIVILEGE LIZENZ_DELETE = Privileges.create("lizenz.delete");
    public final static PRIVILEGE LIZENZ_AUDIT = Privileges.create("lizenz.audit");

    public final static PRIVILEGE_GROUPS LIZENZ = Privileges.createGroup(2, "lizenz", LIZENZ_VIEW, LIZENZ_CREATE, LIZENZ_EDIT, LIZENZ_DELETE, LIZENZ_AUDIT);

    // user
    public final static PRIVILEGE USER_LOGIN = Privileges.create("user.login");
    public final static PRIVILEGE USER_VIEW = Privileges.create("user.view");
    public final static PRIVILEGE USER_CREATE = Privileges.create("user.create");
    public final static PRIVILEGE USER_EDIT = Privileges.create("user.edit", NEED_A_REASON_COMMENT);
    public final static PRIVILEGE USER_DELETE = Privileges.create("user.delete");
    public final static PRIVILEGE USER_AUDIT = Privileges.create("user.audit");

    public final static PRIVILEGE_GROUPS USER = Privileges.createGroup(3, "user", USER_LOGIN, USER_VIEW, USER_CREATE, USER_EDIT, USER_DELETE, USER_AUDIT);


    // user groups
    public final static PRIVILEGE USER_GROUP_VIEW = Privileges.create("user_group.view");
    public final static PRIVILEGE USER_GROUP_CREATE = Privileges.create("user_group.create");
    public final static PRIVILEGE USER_GROUP_EDIT = Privileges.create("user_group.edit");
    public final static PRIVILEGE USER_GROUP_DELETE = Privileges.create("user_group.delete", NEED_A_REASON_COMMENT);

    public final static PRIVILEGE_GROUPS USER_GROUP = Privileges.createGroup(4, "user_group", USER_GROUP_VIEW, USER_GROUP_CREATE, USER_GROUP_EDIT, USER_GROUP_DELETE);


    //department
    public final static PRIVILEGE DEPARTMENT_VIEW = Privileges.create("department.view");
    public final static PRIVILEGE DEPARTMENT_CREATE = Privileges.create("department.create");
    public final static PRIVILEGE DEPARTMENT_EDIT = Privileges.create("department.edit");
    public final static PRIVILEGE DEPARTMENT_DELETE = Privileges.create("department.delete");

    public final static PRIVILEGE_GROUPS DEPARTMENT = Privileges.createGroup(5, "department", DEPARTMENT_VIEW, DEPARTMENT_CREATE, DEPARTMENT_EDIT, DEPARTMENT_DELETE);

    //companies
    public final static PRIVILEGE COMPANY_VIEW = Privileges.create("company.view");
    public final static PRIVILEGE COMPANY_CREATE = Privileges.create("company.create");
    public final static PRIVILEGE COMPANY_EDIT = Privileges.create("company.edit");
    public final static PRIVILEGE COMPANY_DELETE = Privileges.create("company.delete");

    public final static PRIVILEGE_GROUPS COMPANY = Privileges.createGroup(6, "company", COMPANY_VIEW, COMPANY_CREATE, COMPANY_EDIT, COMPANY_DELETE);

    //companies
    public final static PRIVILEGE DOCUMENT_VIEW = Privileges.create("document.view");
    public final static PRIVILEGE DOCUMENT_CREATE = Privileges.create("document.create");
    public final static PRIVILEGE DOCUMENT_EDIT = Privileges.create("document.edit");
    public final static PRIVILEGE DOCUMENT_DELETE = Privileges.create("document.delete");

    public final static PRIVILEGE_GROUPS DOCUMENT = Privileges.createGroup(7, "document", DOCUMENT_VIEW, DOCUMENT_CREATE, DOCUMENT_EDIT, DOCUMENT_DELETE);


    public final static PRIVILEGE AI_CHAT = Privileges.create("ai.chat");
    public final static PRIVILEGE AI_CREATE_MODEL = Privileges.create("ai.create.model");
    public final static PRIVILEGE AI_EDIT_MODEL = Privileges.create("ai.edit.model");
    public final static PRIVILEGE AI_DELETEMODEL = Privileges.create("ai.delete.model");

    public final static PRIVILEGE_GROUPS AI = Privileges.createGroup(8, "ai", AI_CHAT, AI_CREATE_MODEL, AI_EDIT_MODEL, AI_DELETEMODEL);


    public final static PRIVILEGE_GROUPS ADMINISTRATOR = Privileges.createGroup(1, "administrator", LIZENZ, USER, USER_GROUP, DEPARTMENT, COMPANY, DOCUMENT, AI);

}
