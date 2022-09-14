package com.develop.project_auth.domain.service;

import com.develop.project_auth.domain.exception.PermissionNotFoundException;
import com.develop.project_auth.domain.model.Permission;
import com.develop.project_auth.domain.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

	private final PermissionRepository permissionRepository;

	public PermissionService(
			PermissionRepository permissionRepository) {
		this.permissionRepository = permissionRepository;
	}

	public Permission findOrFail(Long id) {
		return permissionRepository.findById(id)
			.orElseThrow(() -> new PermissionNotFoundException(id));
	}
	
}
