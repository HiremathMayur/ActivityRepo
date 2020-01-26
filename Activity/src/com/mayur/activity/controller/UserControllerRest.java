package com.mayur.activity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayur.activity.dto.User;
import com.mayur.activity.service.UserService;

@RestController
@RequestMapping("/Users")
public class UserControllerRest {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public User add(@RequestBody User user, HttpServletRequest request) {
		System.out.println("UserControllerRest::add( "+user.getUserName()+ " ) called");
		System.out.println("Request context path :"+request.getRequestURL());
		user.setUri(request.getRequestURL().toString()+"/"+user.getUserName());
		
		return userService.add(user);
	}
	
	@GetMapping("/{userName}")
	public User getUer(@PathVariable("userName") String userName, HttpServletRequest request) {
		
		User user = userService.get(userName);
		user.setUri(request.getRequestURL().toString());
		
		return user;
	}
	
	@PutMapping("/{userName}")
	public User update(@PathVariable("userName") String userName, @RequestBody User user) {
		System.out.println("UserControllerRest::update( "+userName+ " ) called");
		userService.update(userName, user);
		return user;
	}
	
	@DeleteMapping("{userName}")
	public User delete(@PathVariable("userName") String userName) {
		System.out.println("UserControllerRest::delete( "+userName+" ) called");
		return userService.delete(userName);
	}
	
}