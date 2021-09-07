package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Authority;


public interface IAuthorityService {
	List< Authority > findAll();
	List< Authority > findActiveInactive();
	Authority findById(long id)throws Exception;
	Authority createAuthority (Authority auth) throws Exception;
	
	void deleteAuthority (long id) throws Exception  ;
	
	Authority updateAuthority(Authority auth) throws Exception;

	
	public Authority deleteUpdateAuthority(Authority auth) throws Exception;

}
