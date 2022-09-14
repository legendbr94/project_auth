package com.develop.project_auth.domain.exception;

public abstract class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(String message) {
		super(message);
	}
	
}
