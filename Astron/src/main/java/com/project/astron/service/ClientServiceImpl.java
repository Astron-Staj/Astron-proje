package com.project.astron.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Client;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.repository.ClientDataRepository;
import com.project.astron.repository.UserDataRepository;

@Service
public class ClientServiceImpl implements IClientService{

	@Autowired
	ClientDataRepository clientDataRepository;
	
	
	@Autowired 
	UserDataRepository userDataRepository;
	
	
	@Override
	public List<Client> findAll() {
		List <Client> clients= clientDataRepository.findAll();
		List<Client> activeClients=new ArrayList<Client>();
		for (Client client : clients) {
			if(client.state)
				activeClients.add(client);
		}
		
		return activeClients;
	}

	@Override
	public List<Client> findActiveInactive() {
		return clientDataRepository.findAll();
	}
	

	@Override
	public Client findByCode(String code) throws Exception{
		
		Client cli =clientDataRepository.findByCode(code);
		if(cli==null)
			throw new Exception("  Sistem  Bulunamadı  " );
		else 
			return cli;
	
	
	}



	@Override
	public void createClient(Client client) throws Exception  {
		
		if(clientDataRepository.findByCode(client.getCode())==null) {
			clientDataRepository.save(client);
		}
		else {
			throw new Exception("Sistem zaten kayıtlı!");
		}
		
	}



	@Override
	public Optional<Client> findById(long id) {
		
		return clientDataRepository.findById(id);
	}



	@Override
	public void deleteClient(Client client) throws Exception {
		if(!client.getState()) {
			throw new Exception("Kayıt Zaten Pasif Durumda !");
		}
		else
		{	
			this.deleteUpdateClient(client);
		}
	}

	
	@Override
	public void deleteUpdateClient(Client client) throws Exception {
		boolean isUsing=false;
		List<User> users =userDataRepository.findAll();

			for (User user : users) {
				for (Client cli: user.getClients()) {
					if(cli.getCode().equals(client.getCode()))
						isUsing=true;
				}
				
			}	
		
		
		if(isUsing)
			throw new Exception("Silmek istediğiniz kayıt kullanılıyor!");
		else
		 clientDataRepository.save(client);
	}



	@Override
	public void updateClient(Client client) throws Exception {
		boolean isUsing=false;
		List<User> users =userDataRepository.findAll();
		if (!client.state) {
			for (User user : users) {
				for (Client cli: user.getClients()) {
					if(cli.getId()==client.id)
						isUsing=true;
				}
				
			}	
		}
		
		if(isUsing)
			throw new Exception("Pasif hale getirmek istediğiniz kayıt kullanılıyor!");
		else
		 clientDataRepository.save(client);
	}

	
	
	
	
	
	
}
