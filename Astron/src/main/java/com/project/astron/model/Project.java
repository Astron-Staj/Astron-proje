package com.project.astron.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "project", schema = "public")
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	
	@Column(name="project_name")
	public String projectName;
	
	@Column(name="task_count")
	public long taskCount;
	
	@Column(name="site_map_id",nullable = false)
	public long siteMapId;

	
	
	@ManyToOne
    @JoinColumn(name="site_map_id", nullable=false,referencedColumnName = "id", insertable=false, updatable=false)
    private SiteMap siteMap ;



	public Project(long id, String projectName, long taskCount, long siteMapId, SiteMap siteMap) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.taskCount = taskCount;
		this.siteMapId = siteMapId;
		this.siteMap = siteMap;
	}



	public Project(String projectName, long taskCount, long siteMapId, SiteMap siteMap) {
		
		this.projectName = projectName;
		this.taskCount = taskCount;
		this.siteMapId = siteMapId;
		this.siteMap = siteMap;
	}


	public Project() {
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
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}



	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	/**
	 * @return the taskCount
	 */
	public long getTaskCount() {
		return taskCount;
	}



	/**
	 * @param taskCount the taskCount to set
	 */
	public void setTaskCount(long taskCount) {
		this.taskCount = taskCount;
	}



	/**
	 * @return the siteMapId
	 */
	public long getSiteMapId() {
		return siteMapId;
	}



	/**
	 * @param siteMapId the siteMapId to set
	 */
	public void setSiteMapId(long siteMapId) {
		this.siteMapId = siteMapId;
	}



	/**
	 * @return the siteMap
	 */
	public SiteMap getSiteMap() {
		return siteMap;
	}



	/**
	 * @param siteMap the siteMap to set
	 */
	public void setSiteMap(SiteMap siteMap) {
		this.siteMap = siteMap;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((siteMap == null) ? 0 : siteMap.hashCode());
		result = prime * result + (int) (siteMapId ^ (siteMapId >>> 32));
		result = prime * result + (int) (taskCount ^ (taskCount >>> 32));
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
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (siteMap == null) {
			if (other.siteMap != null)
				return false;
		} else if (!siteMap.equals(other.siteMap))
			return false;
		if (siteMapId != other.siteMapId)
			return false;
		if (taskCount != other.taskCount)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", taskCount=" + taskCount + ", siteMapId="
				+ siteMapId + ", siteMap=" + siteMap + "]";
	}

	
	
	
	
	
}
