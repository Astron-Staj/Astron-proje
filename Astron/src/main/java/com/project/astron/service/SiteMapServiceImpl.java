package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.SiteMap;
import com.project.astron.repository.SiteMapDataRepository;
@Service
public class SiteMapServiceImpl implements ISiteMapService {

	@Autowired
	SiteMapDataRepository siteMapDataRepository;
	
	@Override
	public List<SiteMap> findAll() {
		return siteMapDataRepository.findAll();
	}

	@Override
	public void createSiteMap(SiteMap sitemap) {
		siteMapDataRepository.save(sitemap);
		
	}

	@Override
	public Optional<SiteMap> findById(long id) {
		
		return siteMapDataRepository.findById(id);
	}

	@Override
	public void updateSiteMap(SiteMap siteMap) {
		siteMapDataRepository.save(siteMap);
		
	}

	@Override
	public void deleteSiteMap(SiteMap siteMap) {

		siteMapDataRepository.delete(siteMap);
	}
	
	

	
	
	
}
