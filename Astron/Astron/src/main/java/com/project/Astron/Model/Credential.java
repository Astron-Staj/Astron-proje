package com.project.astron.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "credential", schema = "public")
public class Credential {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	
	@Column(name="username")
	public String username;
	
	@Column(name="password")
	public String password;
	
	@Column(name="user_id",nullable = false)
	public long userId;
	
	

	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", insertable=false, updatable=false)
	User user;


	public Credential(String username, String password, int userId, User user) {
		
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.user = user;
	}



	public Credential(String username, String password) {
		
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.user = user;
	}

	
	
	public Credential() {
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Credential [id=" + id + ", username=" + username + ", password=" + password + ", userId=" + userId
				+ ", user=" + user + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credential other = (Credential) obj;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userId != other.userId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



		

		
	
	
	


	
}
