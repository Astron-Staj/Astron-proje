package com.project.astron.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Authority;
import com.project.astron.model.Project;
import com.project.astron.model.Template;
import com.project.astron.repository.AuthorityDataRepository;
import com.project.astron.repository.TemplateDataRepository;


@Service
public class AuthorityServiceImpl implements IAuthorityService{

	@Autowired
	AuthorityDataRepository authorityDataRepository;
	
	@Autowired
	TemplateDataRepository templateDataRepository;
	
	@Override
	public List<Authority> findAll() {
		List <Authority> auths= authorityDataRepository.findAll();
		List<Authority> activeAuths=new ArrayList<Authority>();
		for (Authority authority : auths) {
			if(authority.state)
				activeAuths.add(authority);
		}
		
		return activeAuths;
	}
	
	
	
	public List<Authority> findActiveInactive(){
		List <Authority> all=authorityDataRepository.findAll();
		return all;
	}



	@Override
	public Authority createAuthority (Authority auth) throws Exception {
		Authority authority =authorityDataRepository.findByName(auth.getName());

		if(authority==null) {
			
			
			authority=authorityDataRepository.save(auth);
			return authority;
		}		
		else
			throw new Exception("Yetki zaten kayıtlı!");
		
	}



	

	



	@Override
	public Authority findById(long id) throws Exception {
		
		
		if(authorityDataRepository.findById(id).get().equals(null))
			throw new Exception("Kayıt Bulunamadı");
		else
		return authorityDataRepository.findById(id).get() ;
	}
	
	@Override
	public void deleteAuthority(long id) throws Exception {
		Authority auth =this.findById(id);
		
		if(auth==null) {
			throw new Exception("Kayıt Bulunamadı");
		}		
		else {
			
			
			if(!auth.isState())
				throw new Exception("Kayıt Zaten Pasif Durumda !");
			else {	
			this.deleteUpdateAuthority(auth);
			}
		}
	}


	
	
	@Override
	public Authority deleteUpdateAuthority(Authority auth) throws Exception {
		boolean isUsing=false;
		
			List<Template> templates = templateDataRepository.findAll();
			for (Template temp : templates) {
			for (Authority athrty : temp.getAuths()) {	
				if(athrty.getName()==auth.getName()) {		
					isUsing=true;
			}
			}
		}	
			
			
			if(isUsing) {
			throw new Exception("Silmeye çalıştığınız kayıt kullanılıyor.");
			}
			
			else {
				auth.setState(false);
			return authorityDataRepository.save(auth);
			}

	}
	

	@Override
	public Authority updateAuthority(Authority auth) throws Exception {
		boolean isUsing=false;
		
		List<Template> templates = templateDataRepository.findAll();
		if (!auth.state) {
			
			for (Template temp : templates) {
			for (Authority athrty : temp.getAuths()) {
				if(athrty.getId()==auth.id) {
					isUsing=true;
				
				}
					
			}
			
		}	
			}
			
		List<Authority> list=this.findActiveInactive();
		boolean exist=false;
		for (Authority prjct : list) {
			if(prjct.getName().equals(auth.getName())&&prjct.getId()!=auth.getId())
				exist=true;
		}
		
			if(exist)
				throw new Exception("Aynı isimle yetki mevcut");
			else
			{
			if(isUsing) {
				auth.setState(true);
			throw new Exception("Pasif hale getirmeye çalıştığınız kayıt kullanılıyor.");
			
			}
			else
			return authorityDataRepository.save(auth);
			}
	}

	
}
