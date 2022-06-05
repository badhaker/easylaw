package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListResponse {
	private Long id;
	private String name;
	private String email;
	private Long contactNo;
	private String userIdURL;
	private Boolean isActive;
    private Boolean isSuspend;
}
