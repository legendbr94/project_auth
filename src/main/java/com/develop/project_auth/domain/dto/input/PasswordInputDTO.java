package com.develop.project_auth.domain.dto.input;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordInputDTO {
	

	@NotBlank
	private String currentPassword;
	

	@NotBlank
	private String newPassword;
}
