package com.develop.project_auth.controller;

import com.develop.project_auth.domain.dto.GroupDTO;
import com.develop.project_auth.domain.dto.input.GroupInputDTO;
import com.develop.project_auth.domain.model.Group;
import com.develop.project_auth.domain.repository.GroupRepository;
import com.develop.project_auth.domain.service.GroupService;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {

  private final GroupRepository groupRepository;
  private final GroupService groupService;
  private final ModelMapper modelMapper;

  public GroupController(GroupRepository groupRepository,
      GroupService groupService, ModelMapper modelMapper) {
    this.groupRepository = groupRepository;
    this.groupService = groupService;
    this.modelMapper = modelMapper;
  }


  @GetMapping
  public List<GroupDTO> findAll() {
    List<Group> groupList = groupRepository.findAll();
    return toCollectionModel(groupList);
  }

  @GetMapping("/{id}")
  public GroupDTO findById(@PathVariable Long id) {
    Group group = groupService.findOrFail(id);
    return toModel(group);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GroupDTO create(@RequestBody @Valid GroupInputDTO groupInputDTO) {
    Group group = toDomainObject(groupInputDTO);
    group = groupService.save(group);
    return toModel(group);
  }

  @PutMapping("/{id}")
  public GroupDTO update(@PathVariable Long id,
      @RequestBody @Valid GroupInputDTO groupInputDTO) {
    Group currentGroup = groupService.findOrFail(id);
    copyToDomainObject(groupInputDTO, currentGroup);
    currentGroup = groupService.save(currentGroup);
    return toModel(currentGroup);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long id) {
    groupService.delete(id);
  }

  private GroupDTO toModel(Group group) {
    return modelMapper.map(group, GroupDTO.class);
  }

  private List<GroupDTO> toCollectionModel(Collection<Group> groups) {
    return groups.stream()
        .map(group -> toModel(group))
        .collect(Collectors.toList());
  }

  private Group toDomainObject(GroupInputDTO groupInputDTO) {
    return modelMapper.map(groupInputDTO, Group.class);
  }

  private void copyToDomainObject(GroupInputDTO groupInputDTO, Group group) {
    modelMapper.map(groupInputDTO, group);
  }
}
