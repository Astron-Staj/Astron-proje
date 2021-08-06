package com.project.astron.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.User;
import com.project.astron.repository.UserDataRepository;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserDataRepository userDataRepository;
	
	
	@Override
	public List<User> findAll() {
		List<User> users=userDataRepository.findAll();
		return users ;
	}

	
	
	
}
