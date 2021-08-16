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



	
}
