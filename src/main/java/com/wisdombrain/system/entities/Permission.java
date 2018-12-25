package com.wisdombrain.system.entities;

public class Permission extends ParentPermission {
	private String permission;
	private String describe;
	private String newdate;

	public Permission() {
		super();
	}

	public String getNewdate() {
		return newdate;
    }

	public void setNewdate(String newdate) {
		this.newdate = newdate;
	}

    public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
