package com.project.Astron.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "public")
public class User {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public long id;
	
	@Column(name="email")
	public String email;
	
	@Column(name="first_name")
	public String first_name;
	
	@Column(name="last_name")
	public String last_name;
	
	@Column(name="state")
	public boolean state;
	
	@Column(name="last_login")
	public Date last_login;
	
	@Column(name="created")
	public Date created;
	
	@Column(name="updated")
	public Date updated;
	
	@Column(name="creator")
	public long creator;
	
	@Column(name="updater")
	public long updater;
	
	public User() {
	}
	
	
	public User(int id,String email, String first_name, String last_name, boolean state, Date last_login, Date created,
			Date updated, int creator, int updater) {
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.state = state;
		this.last_login = last_login;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
	}
	
	public User(String email, String first_name, String last_name, boolean state, Date last_login, Date created,
			Date updated, int creator, int updater) {
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.state = state;
		this.last_login = last_login;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
	}
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Date getLast_login() {
		return last_login;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public long getCreator() {
		return creator;
	}
	public void setCreator(long creator) {
		this.creator = creator;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	
	
	
	
	
	
	
}
