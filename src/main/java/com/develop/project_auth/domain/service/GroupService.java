package com.develop.project_auth.domain.service;

import com.develop.project_auth.domain.exception.EntityInUseException;
import com.develop.project_auth.domain.exception.GroupNotFoundException;
import com.develop.project_auth.domain.model.Group;
import com.develop.project_auth.domain.model.Permission;
import com.develop.project_auth.domain.repository.GroupRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {

	private static final String MSG_GROUP_IN_USE
		= "Grupo de código %d não pode ser removido, pois está em uso";
	

	private final GroupRepository groupRepository;
	private final PermissionService permissionService;

	public GroupService(GroupRepository groupRepository,
			PermissionService permissionService) {
		this.groupRepository = groupRepository;
		this.permissionService = permissionService;
	}

	@Transactional
	public Group save(Group group) {
		return groupRepository.save(group);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			groupRepository.deleteById(id);
			groupRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new GroupNotFoundException(id);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(MSG_GROUP_IN_USE, id));
		}
	}

	@Transactional
	public void unlinkPermission(Long groupId, Long permissionId) {
		Group group = findOrFail(groupId);
		Permission permission = permissionService.findOrFail(permissionId);
		
		group.removePermission(permission);
	}
	
	@Transactional
	public void linkPermission(Long groupId, Long permissionId) {
		Group group = findOrFail(groupId);
		Permission permission = permissionService.findOrFail(permissionId);
		
		group.addPermission(permission);
	}
	
	public Group findOrFail(Long id) {
		return groupRepository.findById(id)
			.orElseThrow(() -> new GroupNotFoundException(id));
	}
	
}
