package com.mayur.activity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayur.activity.dto.Activity;
import com.mayur.activity.service.ActivityService;

@RestController
@RequestMapping("/Users/{userName}/Activity")
public class ActivityControllerRest {

	@Autowired
	ActivityService activityService;
	
	@PostMapping
	public Activity add(@PathVariable("userName") String userName, @RequestBody Activity activity) {
		System.out.println("ActivityControllerRest::add( "+ userName+ ", "+ activity.getName()+ " ) called");
		return activityService.add(userName, activity);
	}
	
	@GetMapping("/{id}")
	public Activity getActivity(@PathVariable("userName") String userName, @PathVariable("id") long id) {
		System.out.println("ActivityControllerRest::getActivity( " + userName + ", "+id+" ) called");
		return activityService.get(userName, id);
	}
	
	@GetMapping
	public List<Activity> getActivityByUser(@PathVariable("userName") String userName) {
		System.out.println("ActivityControllerRest::getActivityByUser( " + userName + " ) called");
		return activityService.getActivityByUser(userName);
	}
	
	@DeleteMapping("/{id}")
	public Activity deleteId(@PathVariable("userName") String userName, @PathVariable("id") long id) {
		return activityService.delete(userName, id);
	}
	
	@DeleteMapping
	public int deleteActivityForUser(@PathVariable("userName") String userName) {
		System.out.println("ActivityControllerRest::deleteActivityForUser( " + userName + " ) called");
		return activityService.deleteByUser(userName);
	}
	
	@PutMapping("/{id}")
	public Activity update(@PathVariable("userName") String userName, @PathVariable("id") long id, @RequestBody Activity activity) {
		System.out.println("ActivityControllerRest::update( " + userName + ", "+id+" ) called");
		return activityService.update(userName, id, activity);
	}
	
}