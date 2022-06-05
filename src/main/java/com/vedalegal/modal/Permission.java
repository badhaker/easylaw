package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Permission {

	private Long permissionNo;
	private String permission;
	// private List<Role> roles;
}
