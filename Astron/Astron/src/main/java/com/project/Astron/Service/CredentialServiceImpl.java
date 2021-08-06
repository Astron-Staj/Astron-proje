package com.project.astron.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Credential;
import com.project.astron.model.User;
import com.project.astron.repository.CredentialDataRepository;


@Service
public class CredentialServiceImpl implements ICredentialService{

	@Autowired
	CredentialDataRepository credentialDataRepository;
	
	@Override
	public List<Credential> findAll() {
		List<Credential> credentials=credentialDataRepository.findAll();		
		return credentials;
	}

	

	@Override
	public Credential findByUsername(String username) {
		Credential cre =credentialDataRepository.findByUsername(username);		
		return cre;
	}
	
}
