package com.sky.po;

public class User {
    private String id;

    private String birthday;

    private String name;

    private String appellation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation == null ? null : appellation.trim();
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", birthday=" + birthday + ", name=" + name + ", appellation=" + appellation + "]";
	}
    
    
}