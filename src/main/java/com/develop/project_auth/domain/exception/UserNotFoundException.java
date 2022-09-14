package com.develop.project_auth.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

	public UserNotFoundException(String mensagem) {
		super(mensagem);
	}
	
	public UserNotFoundException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de usuário com código %d", cozinhaId));
	}
	
}
