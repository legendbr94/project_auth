package com.develop.project_auth.domain.dto.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInputDTO {


	@NotBlank
	private String name;
	

	@NotBlank
	@Email
	private String email;
	
}
