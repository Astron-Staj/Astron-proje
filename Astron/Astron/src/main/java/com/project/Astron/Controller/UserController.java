package com.project.Astron.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.Astron.Model.User;
import com.project.Astron.Service.IUserService;
import com.project.Astron.Service.UserServiceImpl;

@RestController
public class UserController {

	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/all")
	public List<User> getAll() {
		return (List<User>)userService.findAll();
	}
	
	
}
