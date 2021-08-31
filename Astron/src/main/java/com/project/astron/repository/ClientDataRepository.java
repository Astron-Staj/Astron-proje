package com.project.astron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.astron.model.Client;
@Repository
public interface ClientDataRepository extends JpaRepository<Client, Long>{

	Client findByCode(String code);
}