package com.vedalegal.response;

	import lombok.Builder;
import lombok.Data;

	@Data
	@Builder
	public class PermissionList {

		private Long id;
		private String permission;
		// private List<Role> roles;
	}

