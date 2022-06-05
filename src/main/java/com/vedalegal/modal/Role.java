package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {

	private Long id;
	private String name;
	// private List<PermissionEntity> Permissions;
	// private List<UserEntity> users;
	private Boolean isActive;
}
