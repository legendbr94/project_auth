package com.develop.project_auth.controller.unsafe;

import com.develop.project_auth.core.util.Paths;
import com.develop.project_auth.core.util.Tags;
import com.develop.project_auth.domain.dto.UserDTO;
import com.develop.project_auth.domain.dto.input.RecoveryInputDTO;
import com.develop.project_auth.domain.dto.input.UserInputDTO;
import com.develop.project_auth.domain.dto.input.UserWithPasswordInputDTO;
import com.develop.project_auth.domain.model.User;
import com.develop.project_auth.domain.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = Tags.PUBLIC_USERS)
@RestController
@RequestMapping(Paths.PUBLIC_USERS)
public class PublicUserController {

  private final ModelMapper modelMapper;
  private final UserService userService;

  public PublicUserController(ModelMapper modelMapper, UserService userService) {
    this.modelMapper = modelMapper;
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@RequestBody @Valid UserWithPasswordInputDTO userInput) {
    User user = toDomainObject(userInput);
    user = userService.save(user);
    return toModel(user);
  }

  @PostMapping(value = "recovery")
  public void recovery(@RequestBody @Valid RecoveryInputDTO recoveryInputDTO) {
    this.userService.recovery(recoveryInputDTO);
  }

  private User toDomainObject(UserInputDTO userInputDTO) {
    return modelMapper.map(userInputDTO, User.class);
  }

  private void copyToDomainObject(UserInputDTO userInputDTO,
      User user) {
    modelMapper.map(userInputDTO, user);
  }

  private UserDTO toModel(User user) {
    return modelMapper.map(user, UserDTO.class);
  }

  private List<UserDTO> toCollectionModel(
      Collection<User> users) {
    return users.stream()
        .map(this::toModel)
        .collect(Collectors.toList());
  }

}
