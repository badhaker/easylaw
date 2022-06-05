package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddAssociate {

	public Long roleId;
	private String name;
	private Long contactNo;
	private String email;
	private String password;

}
