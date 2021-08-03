package com.project.Astron.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Astron.Model.User;
import com.project.Astron.Repository.UserDataRepository;


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
