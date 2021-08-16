package com.project.astron.service;
import java.util.List;

import com.project.astron.model.Credential;
import com.project.astron.model.User;


public interface ICredentialService {
	List < Credential > findAll();
	
	Credential findByUsername(String username) ;
	Credential createCredential (Credential cre) throws Exception;
	void deleteCredential (String username) throws Exception  ;
	void updateCredential (String username) throws Exception;
	long   CredentialCount ();
}
