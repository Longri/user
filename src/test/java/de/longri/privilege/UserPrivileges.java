package de.longri.privilege;


public class UserPrivileges extends Privileges {




    // lizenz
    public final static PRIVILEGE LIZENZ_VIEW = INSTANCE.create("lizenz.view");
    public final static PRIVILEGE LIZENZ_CREATE = INSTANCE.create("lizenz.create");
    public final static PRIVILEGE LIZENZ_EDIT = INSTANCE.create("lizenz.edit");
    public final static PRIVILEGE LIZENZ_DELETE = INSTANCE.create("lizenz.delete");
    public final static PRIVILEGE LIZENZ_AUDIT = INSTANCE.create("lizenz.audit");

    public final static PRIVILEGE_GROUPS LIZENZ = INSTANCE.createGroup(2, "lizenz", LIZENZ_VIEW, LIZENZ_CREATE, LIZENZ_EDIT, LIZENZ_DELETE, LIZENZ_AUDIT);

    // user
    public final static PRIVILEGE USER_LOGIN = INSTANCE.create("user.login");
    public final static PRIVILEGE USER_VIEW = INSTANCE.create("user.view");
    public final static PRIVILEGE USER_CREATE = INSTANCE.create("user.create");
    public final static PRIVILEGE USER_EDIT = INSTANCE.create("user.edit", NEED_A_REASON_COMMENT);
    public final static PRIVILEGE USER_DELETE = INSTANCE.create("user.delete");
    public final static PRIVILEGE USER_AUDIT = INSTANCE.create("user.audit");

    public final static PRIVILEGE_GROUPS USER = INSTANCE.createGroup(3, "user", USER_LOGIN, USER_VIEW, USER_CREATE, USER_EDIT, USER_DELETE, USER_AUDIT);


    // user groups
    public final static PRIVILEGE USER_GROUP_VIEW = INSTANCE.create("user_group.view");
    public final static PRIVILEGE USER_GROUP_CREATE = INSTANCE.create("user_group.create");
    public final static PRIVILEGE USER_GROUP_EDIT = INSTANCE.create("user_group.edit");
    public final static PRIVILEGE USER_GROUP_DELETE = INSTANCE.create("user_group.delete", NEED_A_REASON_COMMENT);

    public final static PRIVILEGE_GROUPS USER_GROUP = INSTANCE.createGroup(4, "user_group", USER_GROUP_VIEW, USER_GROUP_CREATE, USER_GROUP_EDIT, USER_GROUP_DELETE);


    //department
    public final static PRIVILEGE DEPARTMENT_VIEW = INSTANCE.create("department.view");
    public final static PRIVILEGE DEPARTMENT_CREATE = INSTANCE.create("department.create");
    public final static PRIVILEGE DEPARTMENT_EDIT = INSTANCE.create("department.edit");
    public final static PRIVILEGE DEPARTMENT_DELETE = INSTANCE.create("department.delete");

    public final static PRIVILEGE_GROUPS DEPARTMENT = INSTANCE.createGroup(5, "department", DEPARTMENT_VIEW, DEPARTMENT_CREATE, DEPARTMENT_EDIT, DEPARTMENT_DELETE);

    //companies
    public final static PRIVILEGE COMPANY_VIEW = INSTANCE.create("company.view");
    public final static PRIVILEGE COMPANY_CREATE = INSTANCE.create("company.create");
    public final static PRIVILEGE COMPANY_EDIT = INSTANCE.create("company.edit");
    public final static PRIVILEGE COMPANY_DELETE = INSTANCE.create("company.delete");

    public final static PRIVILEGE_GROUPS COMPANY = INSTANCE.createGroup(6, "company", COMPANY_VIEW, COMPANY_CREATE, COMPANY_EDIT, COMPANY_DELETE);

    //companies
    public final static PRIVILEGE DOCUMENT_VIEW = INSTANCE.create("document.view");
    public final static PRIVILEGE DOCUMENT_CREATE = INSTANCE.create("document.create");
    public final static PRIVILEGE DOCUMENT_EDIT = INSTANCE.create("document.edit");
    public final static PRIVILEGE DOCUMENT_DELETE = INSTANCE.create("document.delete");

    public final static PRIVILEGE_GROUPS DOCUMENT = INSTANCE.createGroup(7, "document", DOCUMENT_VIEW, DOCUMENT_CREATE, DOCUMENT_EDIT, DOCUMENT_DELETE);


    public final static PRIVILEGE AI_CHAT = INSTANCE.create("ai.chat");
    public final static PRIVILEGE AI_CREATE_MODEL = INSTANCE.create("ai.create.model");
    public final static PRIVILEGE AI_EDIT_MODEL = INSTANCE.create("ai.edit.model");
    public final static PRIVILEGE AI_DELETEMODEL = INSTANCE.create("ai.delete.model");

    public final static PRIVILEGE_GROUPS AI = INSTANCE.createGroup(8, "ai", AI_CHAT, AI_CREATE_MODEL, AI_EDIT_MODEL, AI_DELETEMODEL);


    public final static PRIVILEGE_GROUPS ADMINISTRATOR = INSTANCE.createGroup(1, "administrator", LIZENZ, USER, USER_GROUP, DEPARTMENT, COMPANY, DOCUMENT, AI);

}
