package com.bhulai.waran.Entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserProfile {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private boolean isAccountActive;
	private Role role;	
	private List<String> access;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAccountActive() {
		return isAccountActive;
	}
	public void setAccountActive(boolean isAccountActive) {
		this.isAccountActive = isAccountActive;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<String> getAccess() {
		return access;
	}
	public void setAccess(List<String> access) {
		this.access = access;
	}
	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", isAccountActive=" + isAccountActive + ", role=" + role + ", access=" + access + "]";
	}
	
	

}
