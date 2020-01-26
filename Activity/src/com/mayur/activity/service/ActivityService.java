package com.mayur.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mayur.activity.dao.ActivityDAO;
import com.mayur.activity.dto.Activity;
import com.mayur.activity.exception.EntityNotFoundException;

@Service
public class ActivityService {
	
	@Autowired
	ActivityDAO activities;
	
	public Activity get(String userName, long id) {
		System.out.println("ActivityService::get( " + userName + ", "+ id+" ) called");
		Activity activity =  activities.get(id);
		
		if (activity == null) {
			throw new EntityNotFoundException(Activity.class, "activityId", String.valueOf(id));
		}
		
		if (activity.getUserName().equals(userName)) {
			return activity;
		}else {
			throw new EntityNotFoundException(Activity.class, "userName", userName, "activityId", String.valueOf(id));
		}
		
	}
	
	public List<Activity> getActivityByUser(String userName) {
		System.out.println("ActivityService::getActivityByUser( " + userName + " ) called");
		return activities.getActivityByUser(userName);
	}
	
	public Activity add(String userName, Activity activity) {
		System.out.println("ActivityService::add( "+userName+", " + activity.getName() + " ) called");
		activity.setUserName(userName);
		activities.add(activity);
		return activity;
	}
	
	public Activity delete(String userName, Long id) {
		System.out.println("ActivityService::delete( "+userName+", " + id + " ) called");
		Activity activity = activities.get(id);
		if (activity == null) {
			throw new EntityNotFoundException(Activity.class, "activityId", String.valueOf(id));
		}
		if (activity.getUserName().equals(userName)) {
			activities.delete(id);
			return activity;
		}else {
			throw new EntityNotFoundException(Activity.class, "userName", userName, "activityId", String.valueOf(id));
		}
	}
	
	public int deleteByUser(String userName) {
		System.out.println("ActivityService::deleteByUser( "+userName+" ) called");
		return activities.deleteByUser(userName);
	}
	
	public Activity update(String userName, long id, Activity activity) {
		System.out.println("ActivityService::update( "+userName+", " + id + " ) called");
		activity.setId(id);
		activity.setUserName(userName);
		Activity dbActivity = activities.get(id);
		if (dbActivity == null) {
			throw new EntityNotFoundException(Activity.class, "activityId", String.valueOf(id));
		}
				
		if(! dbActivity.getUserName().equals(userName)) {
			throw new EntityNotFoundException(Activity.class, "userName", userName, "activityId", String.valueOf(id));
		}
		
		activities.update(activity);
		return activity;
		
	}
}
