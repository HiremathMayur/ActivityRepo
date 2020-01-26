package com.mayur.activity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mayur.activity.dto.User;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final String DB_USER_NAME = "user_name";
	private static final String DB_FIRST_NAME = "first_name";
	private static final String DB_LAST_NAME = "last_name";
	private static final String DB_EMAIL_ID = "email_id";
	private static final String DB_PHONE_NO = "phone_no";
	
	private static final String INSERT_USER_SQL = "insert into USER(user_name, first_name, last_name, email_id, phone_no) values(:userName, :firstName, :lastName, :emailId, :phoneNo)";
	private static final String GET_USER_SQL = "select * from USER where user_name = :userName";
	private static final String UPDATE_USER_SQL = "update USER set first_name=:firstName, last_name=:lastName, email_id=:emailId, phone_no=:phoneNo where user_name=:userName";
	private static final String DELETE_USER_SQL = "delete from USER where user_name=:userName";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		System.out.println("UserDAOImpl::setDataSource() called");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public User get(String userName) {

		User user = new User();
		user.setUserName(userName);
		
		if (namedParameterJdbcTemplate.queryForRowSet(GET_USER_SQL, new BeanPropertySqlParameterSource(user)).next()) {
			return namedParameterJdbcTemplate.queryForObject(GET_USER_SQL, new BeanPropertySqlParameterSource(user), new UserMapper());
		}else {
			return null;
		}

	}

	@Override
	public int add(User user) {
	
		return namedParameterJdbcTemplate.update(INSERT_USER_SQL, new BeanPropertySqlParameterSource(user));
	}

	@Override
	public int update(String userName, User user) {

		user.setUserName(userName);
		return namedParameterJdbcTemplate.update(UPDATE_USER_SQL, new BeanPropertySqlParameterSource(user));
		
	}

	@Override
	public int delete(String userName) {
		
		User user = new User();
		user.setUserName(userName);
		
		return namedParameterJdbcTemplate.update(DELETE_USER_SQL, new BeanPropertySqlParameterSource(user));

	}

	private static final class UserMapper implements RowMapper<User>{

		@Override
		public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			User user = new User();
			user.setUserName(resultSet.getString(DB_USER_NAME));
			user.setFirstName(resultSet.getString(DB_FIRST_NAME));
			user.setLastName(resultSet.getString(DB_LAST_NAME));
			user.setEmailId(resultSet.getString(DB_EMAIL_ID));
			user.setPhoneNo(resultSet.getString(DB_PHONE_NO));
			return user;
		}
		
	}
}