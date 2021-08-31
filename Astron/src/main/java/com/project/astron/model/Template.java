package com.project.astron.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "template", schema = "public")
public class Template {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	
	@Column(name="state")
	public boolean state;
	
	@Column(name="name")
	public String name;
	
	@Column(name="created")
	public Date created;
	
	@Column(name="updated")
	public Date updated;
	
	@Column(name="creator")
	public long creator;
	
	@Column(name="updater")
	public long updater;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "template_authority_relation", joinColumns = { @JoinColumn(name = "template_id") }, inverseJoinColumns = {
            @JoinColumn(name = "authority_id") })
    private List<Authority> auths=new ArrayList<Authority>();
	

	
	 	@ManyToMany(mappedBy = "templates")
	    @JsonIgnore
	    private List<User> users=new ArrayList<User>();
	

	public Template() {
		super();
	}


	public void removeAuth(Authority auth) {
        this.getAuths().remove(auth);
        auth.getTemplates().remove(this);
    }
 
    public void removeAuths() {
        for (Authority auth: new HashSet<>(auths)) {
            removeAuth(auth);
        }
    }
    
    
    
    public void removeUser(User user) {
        this.getUsers().remove(user);
        user.getTemplates().remove(this);
    }
 
    public void removeUsers() {
        for (User user: new HashSet<>(users)) {
            removeUser(user);
        }
    }

    


	public Template( boolean state, String name, Date created, Date updated, long creator, long updater,
			List<Authority> auths) {
	
		this.state = state;
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
		this.auths = auths;
	}


	


	public Template( boolean state, String name, Date created, Date updated, long creator, long updater,
			List<Authority> auths, List<User> users) {
		
		this.state = state;
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
		this.auths = auths;
		this.users = users;
	}




	public Template(String name, boolean state, Date created, long creator) {
		this.name=name;
		this.state=state;
		this.created=created;
		this.creator=creator;
		
	}




	public Template(long id, String name,boolean state, List<Authority> authList,Date created, Date date, long updater,long creator) {
		this.id=id;
		this.auths=authList;
		this.name=name;
		this.updated=date;
		this.updater=updater;
		this.created=created;
		this.creator=creator;
		this.state=state;
	}

	
	
	
	







	

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}




	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}




	/**
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}




	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}




	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}




	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}




	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}




	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}




	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}




	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}




	/**
	 * @return the creator
	 */
	public long getCreator() {
		return creator;
	}




	/**
	 * @param creator the creator to set
	 */
	public void setCreator(long creator) {
		this.creator = creator;
	}




	/**
	 * @return the updater
	 */
	public long getUpdater() {
		return updater;
	}




	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(long updater) {
		this.updater = updater;
	}




	/**
	 * @return the auths
	 */
	public List<Authority> getAuths() {
		return auths;
	}




	/**
	 * @param auths the auths to set
	 */
	public void setAuths(List<Authority> auths) {
		this.auths = auths;
	}




	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}




	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auths == null) ? 0 : auths.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + (int) (creator ^ (creator >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (state ? 1231 : 1237);
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result + (int) (updater ^ (updater >>> 32));
		result = prime * result + ((users == null) ? 0 : users.hashCode());
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
		Template other = (Template) obj;
		if (auths == null) {
			if (other.auths != null)
				return false;
		} else if (!auths.equals(other.auths))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (creator != other.creator)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (state != other.state)
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		if (updater != other.updater)
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}



	
	
	
	
	
	
	
	
}
