package com.mayur.activity.exception;

import java.sql.SQLException;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ActivityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler
	public ResponseEntity<Object> handleServerException (CannotGetJdbcConnectionException ex, WebRequest request){
		String bodyOfResponse = "JDBC connection not accessible";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleEmptyResultData (EmptyResultDataAccessException ex, WebRequest request){
		String bodyOfResponse = ex.getMessage();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);	
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleDataIntegrityViolation (DataIntegrityViolationException ex, WebRequest request){
		String message = "Child records exists for this resource cannot delete parent before child ";
		return buildResponseEntity(new ActivityAPIError(HttpStatus.FAILED_DEPENDENCY, message, ex));
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex){
		ActivityAPIError activityAPIError = new ActivityAPIError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		return buildResponseEntity(activityAPIError);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleActivityCatchAllException(EntityAlreadyExists ex){
		ActivityAPIError activityAPIError = new ActivityAPIError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), ex);
		return buildResponseEntity(activityAPIError);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleCannotAcquireLockException(CannotAcquireLockException ex){
		System.out.println("ActivityExceptionHandler::handleCannotAcquireLockException()::"+ex);
		ActivityAPIError activityAPIError = new ActivityAPIError(HttpStatus.CONFLICT, ex.getMessage(), ex);
		return buildResponseEntity(activityAPIError);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleSQLException(SQLException ex){
			System.out.println("ActivityExceptionHandler::handleCannotAcquireLockException()::"+ex);
			ActivityAPIError activityAPIError = new ActivityAPIError(HttpStatus.CONFLICT, ex.getMessage(), ex);
			return buildResponseEntity(activityAPIError);
	}
	
	private ResponseEntity<Object> buildResponseEntity (ActivityAPIError activityAPIError){
		return new ResponseEntity<Object>(activityAPIError, activityAPIError.getStatus());
	}
}
