package com.develop.project_auth.domain.service;

import com.develop.project_auth.core.util.ObjectUtil;
import com.develop.project_auth.domain.dto.input.RecoveryInputDTO;
import com.develop.project_auth.domain.exception.BusinessException;
import com.develop.project_auth.domain.exception.UserNotFoundException;
import com.develop.project_auth.domain.model.Group;
import com.develop.project_auth.domain.model.User;
import com.develop.project_auth.domain.repository.UserRepository;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

  private final UserRepository userRepository;
  private final GroupService groupService;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository,
      GroupService groupService,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.groupService = groupService;
    this.passwordEncoder = passwordEncoder;
  }


  @Transactional
  public User save(User user) {
    userRepository.detach(user);

    Optional<User> currentUser = userRepository.findByEmail(user.getEmail());

    if (currentUser.isPresent() && !currentUser.get().equals(user)) {
      throw new BusinessException(
          String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));
    }
    if (user.isNew()) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    return userRepository.save(user);
  }

  @Transactional
  public void updatePassword(Long id, String currentPassword, String newPassword) {
    User user = findOrFail(id);

    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
      throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
  }

  @Transactional
  public void unlinkGroup(Long userId, Long groupId) {
    User user = findOrFail(userId);
    Group group = groupService.findOrFail(groupId);
    user.removeGroup(group);
  }

  @Transactional
  public void linkGroup(Long userId, Long groupId) {
    User user = findOrFail(userId);
    Group group = groupService.findOrFail(groupId);
    user.addGroup(group);
  }

  public User findOrFail(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
  }

  public void recovery(RecoveryInputDTO recoveryInputDTO) {
    User user = this.userRepository.findByEmail(recoveryInputDTO.getEmail())
        .orElseThrow(() -> new BusinessException("Usuário não encontrado!"));
    if (ObjectUtil.isNull(recoveryInputDTO.getNewPassword())) {
      user.setPassword(this.passwordEncoder.encode(this.generateRandomPassword()));
      //Send generatedRandonPassword to User...
    } else {
      user.setPassword(this.passwordEncoder.encode(recoveryInputDTO.getNewPassword()));
    }
    this.save(user);
  }

  private String generateRandomPassword() {
    String password;
    do {
      password = RandomStringUtils.randomAlphanumeric(4, 6);
    } while (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=^.{4,6}$).*$"));
    return password;
  }
}
