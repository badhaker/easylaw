package com.vedalegal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vedalegal.entity.UserEntity;
import com.vedalegal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vedalegal.entity.RoleEntity;
import com.vedalegal.exception.InvalidInputException;
import com.vedalegal.exception.RoleAlreadyExistException;
import com.vedalegal.exception.RoleNotExistException;
import com.vedalegal.modal.Role;
// import com.vedalegal.repository.PermissionRepository;
import com.vedalegal.repository.RoleRepository;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;

import javax.transaction.Transactional;

@Component
public class RoleService {
	@Autowired
	RoleRepository roleRepo;

	// @Autowired
	// private PermissionRepository permissionRepo;

	 @Autowired
	 private UserRepository userRepo;

	public List<Role> getAllRoles() {
		List<RoleEntity> roleEntityList = roleRepo.findAll();
		if (roleEntityList.size()<=0) {
			throw new RoleNotExistException(AppConstant.ErrorTypes.ROLE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ROLE_NOT_EXIST_CODE, AppConstant.ErrorMessage.ROLE_NOT_EXIST_MESSAGE);
		}
		List<Role> roleList = roleEntityList.stream().filter(t->t.getActive()==true).map(e -> convertTomodal(e)).collect(Collectors.toList());
		return roleList;
	}

	public String addRole(String role) {
//	System.out.println(" does role exist?" +roleRepo.findByName(role));
//	if(roleRepo.findByName(role).orElse(null)!=null)
//		throw new RoleAlreadyExistException(AppConstant.ErrorTypes.ROLE_ALREADY_EXIST_ERROR,
//			AppConstant.ErrorCodes.ROLE_ALREADY_EXIST_CODE, AppConstant.ErrorMessage.ROLE_ALREADY_EXIST_MESSAGE);
		List<RoleEntity> roleEntityList = roleRepo.getRole(role);
		if(roleEntityList.size()>0)
		{
			return "Unable to add role, Active role already present";
		}
		else
		{
			roleRepo.save(RoleEntity.builder().name(role).build());
			return "Role added successfully";
		}
	}


	public CommonSuccessResponse deleteRoleById(Long id) {
		RoleEntity role = roleRepo.findById(id).orElse(null);
		if(role == null) {
			throw new RoleNotExistException(AppConstant.ErrorTypes.ROLE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ROLE_NOT_EXIST_CODE, AppConstant.ErrorMessage.ROLE_NOT_EXIST_MESSAGE);
		}
		List<UserEntity> userEntity = userRepo.findAllByRoleId(id);
		if(userEntity.size()>0)
		{
			throw new RoleNotExistException(AppConstant.ErrorTypes.ROLE_ASSIGNED_TO_USER,
					AppConstant.ErrorCodes.ROLE_ASSIGNED_TO_USER_ERROR_CODE, AppConstant.ErrorMessage.ROLE_ASSIGNED_TO_USER);
		}
		role.setActive(false);
		roleRepo.save(role);
		return new CommonSuccessResponse(true);
	}
	
public RoleEntity findByIds(Long id) {
		
		if(id == null) {
			throw new RoleNotExistException(AppConstant.ErrorTypes.ROLE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ROLE_NOT_EXIST_CODE, AppConstant.ErrorMessage.ROLE_NOT_EXIST_MESSAGE);
		}
		else {
		return roleRepo.findById(id)
				.orElseThrow(() -> new RoleNotExistException(AppConstant.ErrorTypes.ROLE_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.ROLE_NOT_EXIST_CODE, AppConstant.ErrorMessage.ROLE_NOT_EXIST_MESSAGE));
		}
	}
public Role findByRoleId(Long id) {
	if(id == null) {
		throw new RoleNotExistException(AppConstant.ErrorTypes.ROLE_NOT_EXIST_ERROR,
				AppConstant.ErrorCodes.ROLE_NOT_EXIST_CODE, AppConstant.ErrorMessage.ROLE_NOT_EXIST_MESSAGE);
	}
	else {
	return convertTomodal(roleRepo.findById(id)
			.orElseThrow(() ->new RoleNotExistException(AppConstant.ErrorTypes.ROLE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ROLE_NOT_EXIST_CODE, AppConstant.ErrorMessage.ROLE_NOT_EXIST_MESSAGE)));
	}
}

	public Role convertTomodal(RoleEntity entity) {
		return Role.builder().id(entity.getId()).name(entity.getName())
				.isActive(entity.getActive()).build();
	}

	public RoleEntity convertToEntity(Role modal) {
		// List<UserEntity> userEntityList =
		// userRepo.findAllByRole(convertToEntity(modal));
		
		RoleEntity entity=RoleEntity.builder().name(modal.getName()).build();
		entity.setId(modal.getId());
		return entity;
	}

	public String addPermission(String permission, Long roleId) {
		RoleEntity role=roleRepo.findById(roleId).orElse(null);
		if (role==null || role.getActive()==false) {
			throw new RoleNotExistException(AppConstant.ErrorTypes.ROLE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ROLE_NOT_EXIST_CODE, AppConstant.ErrorMessage.ROLE_NOT_EXIST_MESSAGE);
		
		}
		role.setPermissions(permission);
		roleRepo.save(role);
		return "Permission added successfully";
	}

	public String getPermissions(String role)
	{
		String permissions = roleRepo.getRolePermissions(role);
		return permissions;
	}

}
