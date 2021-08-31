package com.project.astron.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "authority", schema = "public")
public class Authority {

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

	

	
	
	

 	@ManyToMany(mappedBy = "auths")
    @JsonIgnore
    private List<Template> templates=new ArrayList<Template>();
	
	
	public Authority() {
		super();
	}






	public Authority( boolean state, String name, Date created, Date updated, long creator, long updater) {
		
		this.state = state;
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
	}






	public Authority( boolean state, String name, Date created, Date updated) {
		super();
		this.state = state;
		this.name = name;
		this.created = created;
		this.updated = updated;
	}









	public Authority(boolean state, String name, Date created, Date updated, long creator, long updater,
			List<Template> templates) {
		super();
		this.state = state;
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
		this.templates = templates;
	}


	 public void removeTemplate(Template temp) {
	        this.getTemplates().remove(temp);
	        temp.getAuths().remove(this);
	    }
	 
	    public void removeTemplates() {
	        for (Template temp: new HashSet<>(templates)) {
	            removeTemplate(temp);
	        }
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
	 * @return the templates
	 */
	public List<Template> getTemplates() {
		return templates;
	}






	/**
	 * @param templates the templates to set
	 */
	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}











	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + (int) (creator ^ (creator >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Authority other = (Authority) obj;
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
		return "Authority [id=" + id + ", state=" + state + ", name=" + name + ", created=" + created + ", updated="
				+ updated + ", creator=" + creator + ", updater=" + updater + "]";
	}

	
	


	
	
	
	



	
	
}
