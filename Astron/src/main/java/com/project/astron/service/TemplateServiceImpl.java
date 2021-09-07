package com.project.astron.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dom4j.util.UserDataAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Authority;
import com.project.astron.model.Project;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.repository.TemplateDataRepository;
import com.project.astron.repository.UserDataRepository;

@Service
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	TemplateDataRepository templateDataRepository;
	
	@Autowired
	UserDataRepository userDataRepository;
	
	
	@Override
	public List<Template> findAll() {
		List <Template> temps= templateDataRepository.findAll();
		List<Template> activeTemps=new ArrayList<Template>();
		for (Template temp : temps) {
			if(temp.state)
				activeTemps.add(temp);
		}
		
		return activeTemps;
		
	}
	

	@Override
	public List<Template> findActiveInactive() {
		List<Template> temps=templateDataRepository.findAll();
		
		return temps;
	}



	@Override
	public Template findByName(String name) {
	
	return templateDataRepository.findByName(name);
	}


	@Override
	public void deleteTemplate(Template template) throws Exception {
		templateDataRepository.delete(template);
		
	}


	@Override
	public Optional<Template> findTemplate(long id) {
		
		Optional<Template> template =templateDataRepository.findById(id);
		return	 template;
	
	}


	@Override
	public Template updateTemplate(Template template)  throws Exception  {
		boolean isUsing=false;
		List<User> users =userDataRepository.findAll();
		if (!template.state) {
			for (User user : users) {
				for (Template tmplt : user.getTemplates()) {
					if(tmplt.getId()==template.id)
						isUsing=true;
				}
				
			}	
		}
		
		List<Template> list=this.findActiveInactive();
		boolean exist=false;
		for (Template prjct : list) {
			if(prjct.getName().equals(template.getName())&&prjct.getId()!=template.getId())
				exist=true;
		}
		
			if(exist)
				throw new Exception("Aynı isimle yetki şablonu mevcut");
			else {

		if(isUsing)
			throw new Exception("Pasif hale getirmeye çalıştığınız kayıt kullanılıyor.");
		else
		return templateDataRepository.save(template);
	}
	}

	@Override
	public void deleteTemplateById(long id) throws Exception {
		Optional <Template> template=templateDataRepository.findById(id);
		
		
		if(!template.get().isState()) {
			throw new Exception("Kayıt Zaten Pasif Durumda !");
		}
		else
		{	
			this.deleteUpdateTemplate(template.get());
		}
		
	}
	
	
	public Template deleteUpdateTemplate(Template template)  throws Exception  {
		Optional <Template> t=templateDataRepository.findById(template.id);
		boolean isUsing=false;
		if(t!=null)
		{
		List<User> users =userDataRepository.findAll();

			for (User user : users) {
				for (Template tmplt : user.getTemplates()) {
					if(tmplt.getName()==template.getName())
						isUsing=true;
				}
				
			}	
		
		
		if(isUsing)
			throw new Exception("Silmeye çalıştığınız kayıt kullanılıyor.");
		else
		{
			template.setState(false);
		return templateDataRepository.save(template);
		}
		
		}
		else {
			throw new Exception("Kayıt bulunamadı");
		}
	}

	


	@Override
	public Template findById(long id)throws Exception {
		
		if( templateDataRepository.getById(id)==null)
			throw new Exception("Kayıt Bulunamadı");
		else 
			return templateDataRepository.getById(id);
	}	


	@Override
	public Template saveTemplate(Template cre) throws Exception {
		
		
		if (templateDataRepository.findByName(cre.getName())==null) {
			return templateDataRepository.save(cre);
		}
		else {
			throw new Exception("Yetki şablonu zaten kayıtlı!");
		}
	}

	
	



	

	
	
	
	
}
