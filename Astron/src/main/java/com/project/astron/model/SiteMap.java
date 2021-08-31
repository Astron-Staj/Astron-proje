package com.project.astron.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "site_map", schema = "public")
public class SiteMap {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	
	@Column(name="url")
	public String url;
	
	@Column(name="created")
	public Date created;
	
	@Column(name="updated")
	public Date updated;
	
	@Column(name="creator")
	public long creator;
	
	@Column(name="updater")
	public long updater;
	
	@Column(name="client_id",nullable = false)
	public long clientId;

	@ManyToOne
    @JoinColumn(name="client_id", nullable=false,referencedColumnName = "id", insertable=false, updatable=false)
    private Client client;
	
	
	@OneToMany(mappedBy="siteMap",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Project> projects=new ArrayList<Project>();


	public SiteMap(String url, Date created, Date updated, long creator, long updater, long clientId, Client client,
			List<Project> projects) {
		super();
		this.url = url;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
		this.clientId = clientId;
		this.client = client;
		this.projects = projects;
	}

	public SiteMap(String url, Date created, Date updated, long creator, long updater, long clientId, Client client
		) {
		super();
		this.url = url;
		this.created = created;
		this.updated = updated;
		this.creator = creator;
		this.updater = updater;
		this.clientId = clientId;
		this.client = client;

	}

	public SiteMap() {
		super();
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the clientId
	 */
	public long getClientId() {
		return clientId;
	}


	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}


	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}


	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}


	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}


	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + (int) (clientId ^ (clientId >>> 32));
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + (int) (creator ^ (creator >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((projects == null) ? 0 : projects.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result + (int) (updater ^ (updater >>> 32));
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		SiteMap other = (SiteMap) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (clientId != other.clientId)
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
		if (projects == null) {
			if (other.projects != null)
				return false;
		} else if (!projects.equals(other.projects))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		if (updater != other.updater)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SiteMap [id=" + id + ", url=" + url + ", created=" + created + ", updated=" + updated + ", creator="
				+ creator + ", updater=" + updater + ", clientId=" + clientId + ", client=" + client + ", projects="
				+ projects + "]";
	}
	
	
	
	
	
	
	
}
