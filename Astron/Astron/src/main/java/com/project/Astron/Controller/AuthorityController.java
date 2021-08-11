package com.project.astron.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.astron.model.Authority;
import com.project.astron.service.IAuthorityService;

@RestController
@RequestMapping("authority")
public class AuthorityController {

	@Autowired
	IAuthorityService authorityService;
	
	
	@PreAuthorize("hasAuthority('AUTHORITY_READ_ALL')")
	@GetMapping("/all")
	public List<Authority> getAll() {
		
		return authorityService.findAll();
	}
	
	
	
	
	
	
}
