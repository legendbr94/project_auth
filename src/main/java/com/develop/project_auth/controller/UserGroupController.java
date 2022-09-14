package com.develop.project_auth.controller;

import com.develop.project_auth.domain.dto.GroupDTO;
import com.develop.project_auth.domain.model.Group;
import com.develop.project_auth.domain.model.User;
import com.develop.project_auth.domain.service.UserService;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users/{id}/groups")
public class UserGroupController {

  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserGroupController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public List<GroupDTO> findAll(@PathVariable Long id) {
    User user = userService.findOrFail(id);
    return toCollectionModel(user.getGroups());
  }

  @DeleteMapping("/{groupId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unlink(@PathVariable Long id, @PathVariable Long groupId) {
    userService.unlinkGroup(id, groupId);
  }

  @PutMapping("/{groupId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void link(@PathVariable Long id, @PathVariable Long groupId) {
    userService.linkGroup(id, groupId);
  }

  public GroupDTO toModel(Group group) {
    return modelMapper.map(group, GroupDTO.class);
  }

  public List<GroupDTO> toCollectionModel(Collection<Group> groups) {
    return groups.stream()
        .map(group -> toModel(group))
        .collect(Collectors.toList());
  }
}
