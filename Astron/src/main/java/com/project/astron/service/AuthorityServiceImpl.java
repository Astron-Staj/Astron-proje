package com.project.astron.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Authority;

import com.project.astron.repository.AuthorityDataRepository;


@Service
public class AuthorityServiceImpl implements IAuthorityService{

	@Autowired
	AuthorityDataRepository authorityDataRepository;
	
	
	
	@Override
	public List<Authority> findAll() {
		List <Authority> auths= authorityDataRepository.findAll();
		
		return auths;
	}



	@Override
	public Authority createAuthority (Authority auth) throws Exception {
		Authority authority =authorityDataRepository.findByName(auth.getName());

		if(authority==null) {
			
			
			authority=authorityDataRepository.save(auth);
			return authority;
		}		
		else
			throw new Exception("authority_already_exists");
		
	}



	@Override
	public void saveAuthority(Authority auth) {
		// TODO Auto-generated method stub
		this.authorityDataRepository.save(auth);
	}

	@Override
	public java.util.Optional<Authority> findAuthById(long id) {
		
		java.util.Optional<Authority> auth =authorityDataRepository.findById(id);
		return	 auth;
	
	}

	public void deletex(long id) {
		authorityDataRepository.deleteById(id);
	}

	@Override
	public void deleteAuthority(Authority auth) throws Exception {
		authorityDataRepository.delete(auth);
		
	}

	@Override
	public Authority findById(long id) {
		// TODO Auto-generated method stub
		//java.util.Optional<Authority> optional=authorityDataRepository.findById(id);
		Authority auth = authorityDataRepository.getById(id);
		
		
		return auth;
	}
	
	@Override
	public void deleteAuthority(long id) throws Exception {
		Authority auth =authorityDataRepository.getById(id);
		
		if(auth==null) {
			throw new Exception("authority_not_found");
		}		
		else {
			
			authorityDataRepository.delete(auth);

		}
	}



	@Override
	public Authority updateAuthority(Authority auth) throws Exception {
		//authorityDataRepository.
		return null;
	}



	@Override
	public Authority get(long id) {
		// TODO Auto-generated method stub
		return authorityDataRepository.getById(id);
	}
}
