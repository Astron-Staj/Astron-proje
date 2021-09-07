package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.astron.model.SiteMap;

public interface ISiteMapService {

	void createSiteMap(SiteMap sitemap) throws Exception;
	List<SiteMap> findAll();
	SiteMap findByUrl(String url);
	Optional<SiteMap> findById(long id) throws Exception;
	void updateSiteMap(SiteMap siteMap)throws Exception;
	void deleteSiteMap(SiteMap siteMap);
}
