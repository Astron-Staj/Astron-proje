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

import com.project.astron.model.User;
import com.project.astron.repository.UserDataRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
    @Autowired
    private CredentialServiceImpl credentialServiceImpl;

    @Override
    @Transactional(readOnly = true)
    public UserDetails  loadUserByUsername(String username) {
        User user = credentialServiceImpl.findByUsername(username).getUser();
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
       // for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        //}

        return new org.springframework.security.core.userdetails.User(user.getUserCredential().getUsername(), user.getUserCredential().getPassword(), grantedAuthorities);
    }
}