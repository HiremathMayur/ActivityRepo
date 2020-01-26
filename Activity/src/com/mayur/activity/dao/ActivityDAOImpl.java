package com.mayur.activity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mayur.activity.dto.Activity;


@Repository
public class ActivityDAOImpl implements ActivityDAO {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final String GET_ACTIVITY_ID_SQL = "select * from ACTIVITY where id = :id";
	private static final String INSERT_ACTIVITY_SQL = "insert into ACTIVITY(name, user_name, attribute) values(:name, :userName, :attribute)";
	private static final String GET_ACTIVITY_BY_USER_SQL = "select * from ACTIVITY where user_name = :userName";
	private static final String DELETE_ACITIVITY_BY_USER_SQL = "delete from ACTIVITY where user_name =:userName";
	private static final String DELETE_ACTIVITY_BY_ID_SQL = "delete from ACTIVITY where id=:id";
	private static final String UPDATE_ACTIVITY_BY_ID_SQL = "update ACTIVITY set name=:name, attribute=:attribute where id = :id";
	
	
	private static final String DB_ID = "id";
	private static final String DB_NAME = "name";
	private static final String DB_USER_NAME = "user_name";
	private static final String DB_ATTRIBUTE = "attribute";
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		System.out.println("ActivityDAOImpl::setDataSource() called");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public Activity get(long id) {
		Activity activity = new Activity();
		activity.setId(id);
		if(namedParameterJdbcTemplate.queryForRowSet(GET_ACTIVITY_ID_SQL, new BeanPropertySqlParameterSource(activity)).next()) {
			return namedParameterJdbcTemplate.queryForObject(GET_ACTIVITY_ID_SQL, new BeanPropertySqlParameterSource(activity), new ActivityMapper());
		} else {
			return null;
		}
	}

	@Override
	public int add(Activity activity) {
		return namedParameterJdbcTemplate.update(INSERT_ACTIVITY_SQL, new BeanPropertySqlParameterSource(activity));
	}

	@Override
	public int update(Activity activity) {
		return namedParameterJdbcTemplate.update(UPDATE_ACTIVITY_BY_ID_SQL, new BeanPropertySqlParameterSource(activity));
	}

	@Override
	public int delete(long id) {
		Activity activity = new Activity();
		activity.setId(id);
		return namedParameterJdbcTemplate.update(DELETE_ACTIVITY_BY_ID_SQL, new BeanPropertySqlParameterSource(activity));
	}

	@Override
	public List<Activity> getActivityByUser(String userName) {
			
		Activity activity = new Activity();
		activity.setUserName(userName);
		
		List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(GET_ACTIVITY_BY_USER_SQL, new BeanPropertySqlParameterSource(activity));
		List<Activity> activityList = new ArrayList<>();
		for(Map<String, Object> dbResult: list) {
			Activity dbActivity = new Activity();
			dbActivity.setId(Long.valueOf(dbResult.get(DB_ID).toString()));
			dbActivity.setName(dbResult.get(DB_NAME).toString());
			dbActivity.setUserName(dbResult.get(DB_USER_NAME).toString());
			dbActivity.setAttribute(dbResult.get(DB_ATTRIBUTE).toString());
			activityList.add(dbActivity);
		}
		
		return activityList;
		
	}

	@Override
	public int deleteByUser(String userName) {
		Activity activity = new Activity();
		activity.setUserName(userName);
		
		return namedParameterJdbcTemplate.update(DELETE_ACITIVITY_BY_USER_SQL, new BeanPropertySqlParameterSource(activity));
	}
	
	private static final class ActivityMapper implements RowMapper<Activity>{

		@Override
		public Activity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Activity activity = new Activity();
			
			activity.setId(resultSet.getLong(DB_ID));
			activity.setName(resultSet.getString(DB_NAME));
			activity.setUserName(resultSet.getString(DB_USER_NAME));
			activity.setAttribute(resultSet.getString(DB_ATTRIBUTE));
			
			return activity;
		}
		
	}

}
