package com.develop.project_auth.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

	public GroupNotFoundException(String message) {
		super(message);
	}
	
	public GroupNotFoundException(Long id) {
		this(String.format("Não existe um cadastro de grupo com código %d", id));
	}
	
}
