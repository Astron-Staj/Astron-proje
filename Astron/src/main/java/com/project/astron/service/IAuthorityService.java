package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Authority;


public interface IAuthorityService {
	List< Authority > findAll();
	
	
	Authority findById(long id);
	Authority createAuthority (Authority auth) throws Exception;
	void saveAuthority(Authority auth);
	void deleteAuthority (long id) throws Exception  ;
	Optional<Authority> findAuthById(long id);
	Authority updateAuthority(Authority auth) throws Exception;
	Authority get(long id);
	void deleteAuthority(Authority auth) throws Exception;

}
