package com.project.astron.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;

@Entity
@Table(name = "user", schema = "public")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	
	@Column(name="email")
	public String email;
	
	@Column(name="first_name")
	public String firstName;
	
	@Column(name="last_name")
	public String lastName;
	
	@Column(name="state")
	public boolean state;
	
	@Column(name="last_login")
	public Date lastLogin;
	
	@Column(name="created")
	public Date created;
	
	@Column(name="updated")
	public Date updated;
	
	@Column(name="creator")
	public long creator;
	
	@Column(name="updater")
	public long updater;
	
	
	@ManyToMany(cascade = CascadeType.ALL) @Fetch(value = FetchMode.SELECT)
    @JoinTable(name = "user_template_relation", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"))
    private Set<Template> templates=new HashSet<Template>();
	
	
	
	public User() {
	}



	public User( String email, String firstName, String lastName, boolean state, Date lastLogin, Date created,
			Date updated, long creator, long updater, Set<Template> templates) {
		
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.state = state;
		this.lastLogin = lastLogin;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
		this.templates = templates;
	}



	public User( String email, String firstName, String lastName, boolean state, Date created,
			Date updated) {
		
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.state = state;
		this.lastLogin = lastLogin;
		this.created = created;
		this.updated = updated;
	
	}


	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public boolean isState() {
		return state;
	}



	public void setState(boolean state) {
		this.state = state;
	}



	public Date getLastLogin() {
		return lastLogin;
	}



	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
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



	public Set<Template> getTemplates() {
		return templates;
	}



	public void setTemplates(Set<Template> templates) {
		this.templates = templates;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + (int) (creator ^ (creator >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (state ? 1231 : 1237);
		result = prime * result + ((templates == null) ? 0 : templates.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result + (int) (updater ^ (updater >>> 32));
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
		User other = (User) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (creator != other.creator)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (state != other.state)
			return false;
		if (templates == null) {
			if (other.templates != null)
				return false;
		} else if (!templates.equals(other.templates))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		if (updater != other.updater)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", state=" + state + ", lastLogin=" + lastLogin + ", created=" + created + ", updated=" + updated
				+ ", creator=" + creator + ", updater=" + updater + ", templates=" + templates + "]";
	}

	

	
	
	
	
	
	
	
	
	
	
}
