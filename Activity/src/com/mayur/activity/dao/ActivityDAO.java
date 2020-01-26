package com.mayur.activity.dao;

import java.util.List;

import com.mayur.activity.dto.Activity;

public interface ActivityDAO {
	
	public Activity get(long id);
	public int add(Activity activity);
	public int update(Activity activity);
	public int delete(long id);
	
	public List<Activity> getActivityByUser(String userName);
	public int deleteByUser(String userName);

}
