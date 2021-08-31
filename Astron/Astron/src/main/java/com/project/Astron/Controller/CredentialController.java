package com.project.astron.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.astron.model.Credential;
import com.project.astron.service.CredentialServiceImpl;

@RestController
@RequestMapping("cre")
public class CredentialController {

	@Autowired
	CredentialServiceImpl credentialService;
	
	
	@PreAuthorize("hasAuthority('CREDENTIAL_READ_ALL')")
	@GetMapping("/all")
	public List<Credential> getAll() {
		return (List<Credential>)credentialService.findAll();
	}
	
}
