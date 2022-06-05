package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class AssociateListResponse {


	private String name;
	private Long assocoateId;
	private String role;
	private Long roleId;
	private String email;
	private Long contactNo;
	private String password;

}
