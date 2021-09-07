package com.project.astron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.astron.model.SiteMap;

@Repository
public interface SiteMapDataRepository extends JpaRepository<SiteMap,Long> {
		SiteMap findByUrl(String url);
}
