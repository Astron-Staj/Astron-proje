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
	public void createSiteMap(SiteMap sitemap) throws Exception {
		
		
		if(this.findByUrl(sitemap.getUrl())==null)
		siteMapDataRepository.save(sitemap);
		else 
		throw new Exception("Site haritası zaten kayıtlı !!");
		
	}

	@Override
	public Optional<SiteMap> findById(long id)  throws Exception{
		Optional<SiteMap> sm=siteMapDataRepository.findById(id);
		if(sm.get().equals(null))
			throw new Exception("Kayıt Bulunamadı");
		else
		return sm; 
	}

	@Override
	public void updateSiteMap(SiteMap siteMap) throws Exception {
		Optional<SiteMap> sm=this.findById(siteMap.id);
		
	
			
			if(this.findByUrl(siteMap.getUrl())!=(null)) {
				if(this.findByUrl(siteMap.getUrl()).getId()!=siteMap.getId())
					throw new Exception("Bu url zaten kayıtlıdır.");
				else
					siteMapDataRepository.save(siteMap);
			}
				
			
				
			else
				siteMapDataRepository.save(siteMap);
		
	}

	@Override
	public void deleteSiteMap(SiteMap siteMap) {

		siteMapDataRepository.delete(siteMap);
	}

	@Override
	public SiteMap findByUrl(String url) {
		
		return siteMapDataRepository.findByUrl(url);
	}
	
	

	
	
	
}
