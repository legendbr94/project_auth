package com.develop.project_auth.controller;

import com.develop.project_auth.domain.dto.UserDTO;
import com.develop.project_auth.domain.dto.input.PasswordInputDTO;
import com.develop.project_auth.domain.dto.input.UserInputDTO;
import com.develop.project_auth.domain.dto.input.UserWithPasswordInputDTO;
import com.develop.project_auth.domain.model.User;
import com.develop.project_auth.domain.repository.UserRepository;
import com.develop.project_auth.domain.service.UserService;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserController {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final UserService userService;

  public UserController(UserRepository userRepository, ModelMapper modelMapper,
      UserService userService) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.userService = userService;
  }


  @GetMapping
  public List<UserDTO> findAll() {
    List<User> userList = userRepository.findAll();
    return toCollectionModel(userList);
  }

  @GetMapping("/{id}")
  public UserDTO findById(@PathVariable Long id) {
    User user = userService.findOrFail(id);
    return toModel(user);
  }


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@RequestBody @Valid UserWithPasswordInputDTO userWithPasswordInputDTO) {
    User user = toDomainObject(userWithPasswordInputDTO);
    user = userService.save(user);
    return toModel(user);
  }

  @PutMapping("/{id}")
  public UserDTO update(@PathVariable Long id, @RequestBody @Valid UserInputDTO userInputDTO) {
    User currentUser = userService.findOrFail(id);
    copyToDomainObject(userInputDTO, currentUser);
    currentUser = userService.save(currentUser);
    return toModel(currentUser);
  }

  @PutMapping("/{id}/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> updatePassword(@PathVariable Long id,
      @RequestBody @Valid PasswordInputDTO password) {
    userService.updatePassword(id, password.getCurrentPassword(), password.getNewPassword());
    return ResponseEntity.noContent().build();
  }

  private User toDomainObject(UserInputDTO userInputDTO) {
    return modelMapper.map(userInputDTO, User.class);
  }

  private void copyToDomainObject(UserInputDTO userInputDTO, User user) {
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
