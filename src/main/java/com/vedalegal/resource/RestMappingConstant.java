package com.vedalegal.resource;

public interface RestMappingConstant {
	
	public interface OTPRequestUri {
		static final String OTP_BASE_URI = "/OTP";
		static final String GET_OTP_EMAIL = "/getEmailOTP";
		static final String VALIDATE_OTP_SMS = "/validateOTPSms";
		static final String VALIDATE_OTP_EMAIL = "/validateOTPEmail";
		static final String GET_OTP_SMS = "/getSmsOTP";

	}
	
	public interface MasterServiceInterfaceUri {

		static final String MASTER_SERVICE_BASE_URI = "/masterService";
		static final String MASTER_SERVICE_CREATE_URI = "/addService";
		static final String MASTER_SERVICE_URI = "/get";
		static final String MASTER_SERVICE_DELETE_URI = "/delete";
		static final String MASTER_SERVICE_UPDATE__URI = "/update";

	}

	public interface RoleUri
	{
		String BASE_URI="/roles";
		String ADD_ROLE_URI="/addRole";
		String GET_ALL_ROLES_URI="/getAllRoles";
		String GET_ROLE_BY_ROLEID="/getRoleById";
		String DELETE_ROLE_URI="/deleteRole";	
	}
}
