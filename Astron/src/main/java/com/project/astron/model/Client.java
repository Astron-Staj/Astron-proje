package com.project.astron.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@Entity
@Table(name = "client", schema = "public")
public class Client {

	
	
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		public long id;
		
		@Column(name="code")
		public String code;
		
		@Column(name="state")
		public boolean state;
		
		@Column(name="name")
		public String name;
		
		@Type(type="org.hibernate.type.BinaryType")
	    @Column(name = "logo")
	    @Basic(fetch = FetchType.LAZY)
	    private byte[] logo;
		
		@Column(name="created")
		public Date created;
		
		@Column(name="updated")
		public Date updated;
		
		@Column(name="creator")
		public long creator;
		
		@Column(name="updater")
		public long updater;
		

	 	@ManyToMany(mappedBy = "clients")
	    @JsonIgnore
	    private List<User> users=new ArrayList<User>();

	 	
	 	@OneToMany(mappedBy="client",fetch = FetchType.LAZY,
	            cascade = CascadeType.ALL)
	    private List<SiteMap> sitemaps=new ArrayList<SiteMap>();


		public Client(String code, boolean state, String name, byte[] logo, Date created, Date updated, long creator,
				long updater, List<User> users, List<SiteMap> sitemaps) {
			super();
			this.code = code;
			this.state = state;
			this.name = name;
			this.logo = logo;
			this.created = created;
			this.updated = updated;
			this.creator = creator;
			this.updater = updater;
			this.users = users;
			this.sitemaps = sitemaps;
		}

		public Client(String code, boolean state, String name, byte[] logo, Date created, Date updated, long creator,
				long updater) {
			super();
			this.code = code;
			this.state = state;
			this.name = name;
			this.logo=logo;
			this.created = created;
			this.updated = updated;
			this.creator = creator;
			this.updater = updater;
			 
		}


		public Client() {
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
		 * @return the code
		 */
		public String getCode() {
			return code;
		}


		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}


		/**
		 * @return the state
		 */
		public boolean getState() {
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
		 * @return the logo
		 */
		public byte[] getLogo() {
			return logo;
		}


		/**
		 * @param logo the logo to set
		 */
		public void setLogo(byte[] logo) {
			this.logo = logo;
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


		/**
		 * @return the sitemaps
		 */
		public List<SiteMap> getSitemaps() {
			return sitemaps;
		}


		/**
		 * @param sitemaps the sitemaps to set
		 */
		public void setSitemaps(List<SiteMap> sitemaps) {
			this.sitemaps = sitemaps;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((code == null) ? 0 : code.hashCode());
			result = prime * result + ((created == null) ? 0 : created.hashCode());
			result = prime * result + (int) (creator ^ (creator >>> 32));
			result = prime * result + (int) (id ^ (id >>> 32));
			result = prime * result + Arrays.hashCode(logo);
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((sitemaps == null) ? 0 : sitemaps.hashCode());
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
			Client other = (Client) obj;
			if (code == null) {
				if (other.code != null)
					return false;
			} else if (!code.equals(other.code))
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
			if (!Arrays.equals(logo, other.logo))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (sitemaps == null) {
				if (other.sitemaps != null)
					return false;
			} else if (!sitemaps.equals(other.sitemaps))
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

		@Override
		public String toString() {
			return "Client [id=" + id + ", code=" + code + ", state=" + state + ", name=" + name +  ", created=" + created + ", updated=" + updated + ", creator=" + creator
					+ ", updater=" + updater + ", users=" + users + ", sitemaps=" + sitemaps + "]";
		}

		
		

		
	 	
		

	 	
	 	

		
		
		
		
	
	
}
