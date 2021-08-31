package com.project.astron.service;
import java.util.List;

import com.project.astron.model.Credential;


public interface ICredentialService {
	List < Credential > findAll();
	Credential findByUserId(long id) ;
	Credential findByUsername(String username) ;
	Credential createCredential (Credential cre) throws Exception;
	void deleteCredential (String username) throws Exception  ;
	void updateCredential (Credential cre) throws Exception;
	long   CredentialCount ();
	
}
