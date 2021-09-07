package com.project.astron.service;
import java.util.List;

import com.project.astron.model.Credential;


public interface ICredentialService {
	List < Credential > findAll();
	Credential findByUserId(long id) ;
	Credential findByUsername(String username) throws Exception ;
	Credential createCredential (Credential cre) throws Exception;
	void deleteCredential (String username,String authUsername) throws Exception  ;
	void updateCredential (Credential cre,long id) throws Exception;
	void deleteUpdateCredential (Credential cre,String username) throws Exception;
	void updateCre (Credential cre) throws Exception;
	long   CredentialCount ();
	Credential findForLogin(String username);
	
}
