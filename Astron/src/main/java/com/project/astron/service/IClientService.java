package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Client;

public interface IClientService {

	List<Client> findAll();
	List<Client> findActiveInactive();
	void createClient(Client client) throws Exception;
	Client findByCode(String code) throws Exception;
	Optional<Client> findById(long id);
	void deleteClient(Client client) throws Exception;
	void updateClient(Client client) throws Exception;
	void deleteUpdateClient(Client client) throws Exception;
}
