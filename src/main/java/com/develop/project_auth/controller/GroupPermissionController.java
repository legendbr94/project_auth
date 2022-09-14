package com.develop.project_auth.controller;

import com.develop.project_auth.domain.dto.PermissionDTO;
import com.develop.project_auth.domain.model.Group;
import com.develop.project_auth.domain.model.Permission;
import com.develop.project_auth.domain.service.GroupService;
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
@RequestMapping(path = "/groups/{id}/permissions")
public class GroupPermissionController {

  private final GroupService groupService;
  private final ModelMapper modelMapper;

  public GroupPermissionController(GroupService groupService, ModelMapper modelMapper) {
    this.groupService = groupService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public List<PermissionDTO> findAll(@PathVariable Long id) {
    Group group = groupService.findOrFail(id);
    return toCollectionModel(group.getPermissions());
  }

  @DeleteMapping("/{permissionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unlink(@PathVariable Long id, @PathVariable Long permissionId) {
    groupService.unlinkPermission(id, permissionId);
  }

  @PutMapping("/{permissionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void link(@PathVariable Long id, @PathVariable Long permissionId) {
    groupService.linkPermission(id, permissionId);
  }

  private PermissionDTO toModel(Permission permission) {
    return modelMapper.map(permission, PermissionDTO.class);
  }

  private List<PermissionDTO> toCollectionModel(Collection<Permission> permissions) {
    return permissions.stream()
        .map(permission -> toModel(permission))
        .collect(Collectors.toList());
  }
}
