package com.mayur.activity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mayur.activity.dao.UserDAO;
import com.mayur.activity.dto.User;
import com.mayur.activity.exception.EntityAlreadyExists;
import com.mayur.activity.exception.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserDAO users;
	
	public User add(User user) {
		if(users.get(user.getUserName()) != null) {
			throw new EntityAlreadyExists(User.class, "userName", user.getUserName());
		}
		users.add(user);
		return user;
	}
	
	public User get(String userName) {
		User user = users.get(userName);
		if (user == null) {
			throw new EntityNotFoundException(User.class, "userName", userName);
		}else {
			return user;
		}
	}
	
	public User update(String userName, User user) {
		if ( users.update(userName, user) == 1) {
			return user;
		}else {
			throw new EntityNotFoundException(User.class, "userName", userName);
		}
	}
	
	public User delete(String userName) {
		User user = users.get(userName);
		if(user == null) {
			throw new EntityNotFoundException(User.class, "userName", userName);		
		}
		
		users.delete(userName);
		
		return user;
		
	}
}