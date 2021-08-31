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


	@Override
	public User createUser(User user) throws Exception {
		
		User saved= userDataRepository.save(user);
		return user;
		
	}

/*
	@Override
	public void deleteUser(long id) throws Exception{
		if(isExist(id))
		userDataRepository.deleteById(id);
		else
			throw new Exception("user not found");
	}
*/

	@Override
	public void updateUser(User user)  throws Exception{
	
		if(isExist(user.getId())) {
			userDataRepository.save(user);
		}
		else
			throw new Exception("user_not_found");
	
	}


	@Override
	public boolean isExist(long id) {
		return userDataRepository.existsById(id);
		
	}


	@Override
	public long userCount() {
		return userDataRepository.count();
	}

	
	
	
	



	
	
	
}
