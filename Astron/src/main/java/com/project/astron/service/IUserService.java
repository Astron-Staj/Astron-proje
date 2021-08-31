package com.project.astron.service;

import java.util.List;

import com.project.astron.model.User;


public interface IUserService {
	List < User > findAll();
	User createUser (User user) throws Exception;
	//void deleteUser (long id) throws Exception  ;
	void updateUser (User user) throws Exception;
	boolean isExist (long id);
	long   userCount ();
}
