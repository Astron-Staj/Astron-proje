package com.project.astron.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.astron.model.Credential;

@Repository
public interface CredentialDataRepository extends JpaRepository<Credential, Long>{
	 Credential findByUsername(String username);
	 Credential findByUserId(long id);
}
