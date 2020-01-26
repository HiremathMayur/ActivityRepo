package com.mayur.activity.dao;

import com.mayur.activity.dto.User;

public interface UserDAO {
	
	public User get(String userName);
	public int add(User user);
	public int update(String userName, User user);
	public int delete(String userName);

}
