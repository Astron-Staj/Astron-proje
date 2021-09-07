package com.project.astron.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.repository.CredentialDataRepository;
import com.project.astron.repository.UserDataRepository;


@Service
public class CredentialServiceImpl implements ICredentialService{

	@Autowired
	CredentialDataRepository credentialDataRepository;
	
	@Autowired
	UserDataRepository userDataRepository;
	
	@Override
	public List<Credential> findAll() {
		List<Credential> credentials=credentialDataRepository.findAll();		
		return credentials;
	}

	
	

	@Override
	public Credential findByUsername(String username) throws Exception  {
		
		Credential cre =credentialDataRepository.findByUsername(username);
	
		if(cre==null)
			throw new Exception("Kullanıcı Bulunamadı");
		else
		return cre;
	}

	


	@Override
	public Credential findForLogin(String username) {
		
		return credentialDataRepository.findByUsername(username);
	}




	@Override
	public Credential createCredential(Credential cre) throws Exception {
		
		Credential credential =credentialDataRepository.findByUsername(cre.getUsername());

		if(credential==null) {
			User user= userDataRepository.save(cre.getUser());
			cre.setUserId(user.getId());
			System.out.println(user.getId());
			credential=credentialDataRepository.save(cre);
			return credential;
		}		
		else
			throw new Exception("Kullanıcı zaten kayıtlı");
		
	}



	@Override
	public void deleteCredential(String username,String authUsername) throws Exception {
		Credential credential =credentialDataRepository.findByUsername(username);

		
			if(!credential.getUser().getState()) {
				throw new Exception("Hesap Zaten Pasif Durumda !");
			}
			else
			{	
				this.deleteUpdateCredential(credential,authUsername);
			}
			
			
			
			//UserDataRepository.delete(user);

		}
	


	@Override
	public void deleteUpdateCredential(Credential cre,String username) throws Exception {
		Credential credential =credentialDataRepository.findByUsername(cre.getUsername());
		
			if(credential.getUsername().equals(username)) {
				
					throw new Exception("Kendi Hesabınızı silemezsiniz!");
				
			}	
				else {
					
					cre.getUser().setState(false);
					credentialDataRepository.save(cre);
					userDataRepository.save(cre.getUser())	;
				}
			}

	@Override
	public void updateCredential(Credential cre,long id) throws Exception {
		Credential credential =credentialDataRepository.findByUsername(cre.getUsername());
		
		
			if(!cre.getUser().getState()&&credential.getUserId()==id) {
				
					throw new Exception("Hesabınızı pasif hale getiremezsiniz!");
				
			}	
				else {
					credentialDataRepository.save(cre);
					userDataRepository.save(cre.getUser())	;
				}
					
			
		
		
	}



	@Override
	public void updateCre(Credential cre) throws Exception {
		Credential credential =credentialDataRepository.findByUsername(cre.getUsername());

		if(credential==null) {
			throw new Exception("Kullannıcı adı bulunamadı");
		}		
		else {
			
					credentialDataRepository.save(cre);
					userDataRepository.save(cre.getUser())	;
				
		}

		
	}




	@Override
	public long CredentialCount() {
			return credentialDataRepository.count(); 
	}



	@Override
	public Credential findByUserId(long id) {
		
		return credentialDataRepository.findByUserId(id);
	}
	
	
	
	
	
	
}
