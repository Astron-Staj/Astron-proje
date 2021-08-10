package com.project.astron.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.astron.model.User;
import com.project.astron.service.IUserService;
import com.project.astron.service.UserServiceImpl;



@RestController
@RequestMapping("user")
public class UserController {

	
	@Autowired
	IUserService userService;
	
	
	@GetMapping("all")
	public List<User> getAll() {
		return userService.findAll();
	}
	
	
}
