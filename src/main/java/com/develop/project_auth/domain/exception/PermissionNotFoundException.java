package com.develop.project_auth.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException {

	public PermissionNotFoundException(String message) {
		super(message);
	}
	
	public PermissionNotFoundException(Long id) {
		this(String.format("Não existe um cadastro de permissão com código %d", id));
	}
	
}
