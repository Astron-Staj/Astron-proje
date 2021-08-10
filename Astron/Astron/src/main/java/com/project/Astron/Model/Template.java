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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

	
	
	@ManyToMany(cascade = CascadeType.ALL) @Fetch(value = FetchMode.SELECT)
    @JoinTable(name = "template_authority_relation", joinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> auths=new HashSet<Authority>();
	
	

	public Template() {
		super();
	}




	public Template( boolean state, String name, Date created, Date updated, long creator, long updater,
			Set<Authority> auths) {
	
		this.state = state;
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
		this.auths = auths;
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public boolean isState() {
		return state;
	}




	public void setState(boolean state) {
		this.state = state;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
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




	public Set<Authority> getAuths() {
		return auths;
	}




	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}




	

	@Override
	public String toString() {
		return "Template [id=" + id + ", state=" + state + ", name=" + name + ", created=" + created + ", updated="
				+ updated + ", creator=" + creator + ", updater=" + updater + ", auths=" + auths + "]";
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auths == null) ? 0 : auths.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + (int) (creator ^ (creator >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (state ? 1231 : 1237);
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
		return true;
	}
	
	
	
	
	
	
	

	
	
	
	
	
	
	
}
