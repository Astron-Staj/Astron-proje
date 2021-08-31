package com.project.astron.service;


import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.astron.model.Authority;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
    @Autowired
    private ICredentialService credentialService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails  loadUserByUsername(String username) {
    	Credential cre = credentialService.findByUsername(username);
    	User user=cre.getUser();
        if (user == null) throw new UsernameNotFoundException(username);
       
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Template template : user.getTemplates()){
        	for(Authority auth: template.getAuths())
            grantedAuthorities.add(new SimpleGrantedAuthority(auth.getName()));
        }
            
        return new org.springframework.security.core.userdetails.User(cre.getUsername(), cre.getPassword(), grantedAuthorities);
    }
}