package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Client;

public interface IClientService {

	List<Client> findAll();
	void createClient(Client client) throws Exception;
	Client findByCode(String code);
	Optional<Client> findById(long id);
	void deleteClient(Client client);
	void updateClient(Client client);
}
