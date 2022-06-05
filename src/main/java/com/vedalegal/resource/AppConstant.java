package com.vedalegal.resource;

public interface AppConstant {
	
	public interface Commons{

		String PAGE_NUMBER = "page";
		String PAGE_DEFAULT_VALUE = "0";
		String PAGE_LIMIT = "limit";
		String PAGE_LIMIT_VALUE = "10";
		String USER_ROLE="User";
		String LAWYER_ROLE="Lawyer";
		String ADMIN_ROLE="Admin";

		
	}
	interface TimeZone {
		Long IST_TIME_ZONE = 19800000L;
		String TIME_FORMAT = "hh:mm a";

	}
	
	interface mailSubject{
		String 	MEETING_SCHEDULE_SUBJECT="Easylaw - Meeting Scheduled";
		
	}
	interface mailBody{
		String 	MEETING_SCHEDULE_BODY="You have a video call scheduled with EasyLaw on  Please use the above link to start the video call. Kindly make sure that you have access to camera and internet for the video call.";
	}
	
	public interface StatusCodes {
		int SUCCESS = 0;
		int FAILURE = 1;
	}

	public interface DeleteStatus {
		int STATUS_ACTIVE = 0;
		int STATUS_DELETE = 1;
	}
	
	public interface OtpService {
		public static final Integer OTP_EXPIRY_TIME = 120;
		public static final String OTP_SUBJECT_LINE = "EasyLaw-OTP Verification";

	}
	
	public interface OtpSmsService {

		public static final String USERID = "easylaw2022";
		public static final String PASSWORD = "easylaw2022";
		public static final String ADMAGISTER_CHANNEL = "Trans";
		public static final String ADMAGISTER_SENDERID = "ESYLAW";
		public static final Integer ADMAGISTER_FLASH_SMS = 0;
		public static final Integer ADMAGISTER_DCS = 0;
		public static final Integer ADMAGISTER_ROUTE = 30;
		public static final String ADMAGISTERURL = "https://admagister.net/api/mt/SendSMS";
		public static final String MESSAGE = "Your EASYLAW verification OTP is: {var1}";

	}

	public interface ErrorCodes {

		String INVALID_INPUT_ERROR_CODE = "102";
		String CATEGORY_NOT_EXIST_CODE = "104";
		String INVALID_OTP_ERROR_CODE = "116";
		String ROLE_NOT_EXIST_CODE = "105";
		String MASTER_SERVICE_NOT_EXIST_CODE = "106";
		String PERMISSION_NOT_EXIST_CODE = "107";
		String FILE_NOT_UPLOADED_CODE="108";
		String SUBSERVICE_NOT_EXIST_CODE = "109";
		String LAWYER_NOT_EXIST_CODE = "110";		
		String ENTITY_NOT_EXISTS_ERROR_CODE = "111";
		String ACCOUNT_ALREADY_EXISTS_ERROR_CODE = "112";
		String INVALID_CREDENTIALS_ERROR_CODE = "113";
		String PASSWORD_DOES_NOT_MATCH_ERROR_CODE = "114";
		String INVALID_TOKEN_ERROR_CODE = "115";
		String MOBILE_NO_ALREADY_EXISTS_ERROR_CODE = "116";
		String UPLOAD_FILE_EXCEPTION_CODE = "117";
		String USER_NOT_EXIST_CODE = "118";	
        String ORDER_NOT_EXIST_CODE = "120";
		String EXPERTISE_NAME_NOT_EXIST_CODE= "119";
		String ASSOCIATE_NOT_EXIST_CODE= "121";
		String PATH_NULL_FOR_FILE_ERROR_CODE = "122";
		String ROLE_NOT_EXISTS_ERROR_CODE = "123";	
		String ENQUIRY_NOT_EXIST_CODE = "124";
		String COMPLAINT_NOT_EXIST_CODE = "125";
		String ROLE_ALREADY_EXIST_CODE="126";
		String ENTITY_NOT_EXIST_CODE = "127";
		String INVALID_ROLE_ERROR_CODE="128";
		String REVIEW_NOT_EXIST_CODE = "129";
		String REVIEW_LIMIT_CODE = "130";
		String EMAIL_CONTACT_ALREADY_EXIST_CODE = "131";
		String USER_SUPENEDED_CODE ="132";
		String ACCOUNT_NOT_APPROVED_CODE = "133";
		String USER_DELETED_CODE ="134";
		String USER_EXIST_IN_DATABASE_ERROR_CODE = "135";
		String USER_EXIST_IN_DATABASE_ERROR_CODES = "135";
		String ROLE_ASSIGNED_TO_USER_ERROR_CODE = "136";
		}

	public interface ErrorTypes {

		String NULL_ENTITY = "Entity not present error";

		String INVALID_INPUT_ERROR = "Invalid Input";
		// String MASTER_SERVICE_EXIST_ERROR = "Does Not Exist";
		String CATEGORY_NOT_EXIST_ERROR = "Not Found";
		String INVALID_OTP_ERROR ="OTP Does Not Match";
		String ROLE_NOT_EXIST_ERROR = "Does Not Exist";
		String MASTER_SERVICE_NOT_EXIST_ERROR = "Does Not Exist";
		String PERMISSION_NOT_EXIST_ERROR = "Does Not Exist";
		String FILE_NOT_UPLOADED_CODE="Not Uploaded";
		String SUBSERVICE_NOT_EXIST_ERROR = "Does Not Exist";
		String LAWYER_NOT_EXIST_ERROR = "Does Not Exist";
		String INVALID_TOKEN_ERROR_TYPE = "Invalid Token ";
		String ENTITY_NOT_EXISTS_ERROR = "Invalid Credentials";
		String ACCOUNT_ALREADY_EXISTS = "Account Already Exists";
		String ACCOUNT_NOT_APPROVED = "Account Approval";
		String INVALID_CREDENTIALS = "Invalid credentials";
		String PASSWORD_DOES_NOT_MATCH = "Password does not match";
		String MOBILE_NO_ALREADY_EXISTS = "Mobile Number Already Exist";
		String UPLOAD_FILE_EXCEPTION = "Upload File Exception";
		String USER_NOT_EXIST_ERROR="Does Not Exist";
        String ORDER_NOT_EXIST_ERROR = "Does Not Exist";
		String EXPERTISE_NAME_NOT_EXIST_ERROR="Does Not Exist";
		String ASSOCIATE_NOT_EXIST_ERROR="Does Not Exist";
		String PATH_NULL_FOR_FILE = "File Path Null Error";
		String ROLE_NOT_EXISTS = "Role Not Exists";
		String ENQUIRY_NOT_EXIST_ERROR = "Does Not Exist";
		String COMPLAINT_NOT_EXIST_ERROR = "Does Not Exist";
		String ROLE_ALREADY_EXIST_ERROR="Already Exist";
		String ENTITY_NOT_EXIST_ERROR="Does Not Exist";
		String EXPERTISE_ENTITY_NOT_EXISTS_ERROR = "Expertise you are trying to add does not Exist in database";
		String INVALID_ROLE_ERROR = "Invalid User Role";
		String REVIEW_NOT_EXIST = "review not exist";
		String REVIEW_LIMIT_EXHAUSTED = "Review Limit Exhausted";
		String EMAIL_CONTACT_ALREADY_EXIST = "Email and Conact already exist";
		String USER_SUPENEDED = "User Suspend temprorary";
		String USER_DELETED = "User deleted by EasyLaw team";
		String USER_EXIST_IN_DATABASE = "User already exist please login";
		String USER_EXIST_IN_DATABASES = "User already exist please Check";
		String ROLE_ASSIGNED_TO_USER="This role is assigned to an user, It cannot be deleted";

	}

	public interface ErrorMessage {

		String INVALID_INPUT_MESSAGE = "Input provided is not valid";
		String INVALID_OTP_MESSAGE = "OTP Does Not Match";
		String MASTER_SERVICE_NOT_EXIST_MESSAGE = "Master Service Does Not Exist in database";
		String PERMISSION_NOT_EXIST_MESSAGE = "Permission does not exist in database.";
		String CATEGORY_NOT_EXIST_MESSAGE = "Category Does Not Exist";
		String ROLE_NOT_EXIST_MESSAGE = "Role does not exist in database.";
		String FILE_NOT_UPLOADED_MESSAGE="File/Image could not be uploaded";
		String SUBSERVICE_NOT_EXIST_MESSAGE = "SubService does not exist in database.";
		String LAWYER_NOT_EXIST_MESSAGE = "Lawyer/User does not exist in database.";
		String ROLE_ALREADY_EXIST_MESSAGE="Role already exists";
		String COMPOSITION_NOT_AVIALABLE = "composition not available this time";
		String NO_ROOM_FIND="Enter valid room name";
		String INVALID_TOKEN_ERROR_MESSAGE = "Invalid  Security Token - Authorization";
		String ENTITY_NOT_EXISTS_ERROR_MESSAGE = "Invalid Credentials";
		String ACCOUNT_ALREADY_EXISTS_ERROR_MESSAGE="Account Already Exists Please go to Login";
		String INVALID_CREDENTIALS_ERROR_MESSAGE = "User Name Or Password did not matched";
		String ACCOUNT_ALREADY_EXISTS_SINGUP_MANUALLY = "Seems like you had signed up manually! Please try again.";
		String PASSWORD_DOES_NOT_MATCH_MESSAGE = "Your Account and Password Not matched";
		String MOBILE_NO_ALREADY_EXISTS_ERROR_MESSAGE = "Mobile Number Already Exist";
		String USER_NOT_EXIST_MESSAGE = "User does not exist in database.";
		String ASSOCIATE_NOT_EXIST_MESSAGE = "Associate does not exist in database.";
		String ORDER_NOT_EXIST_MESSAGE = "You haven't booked a lawyer, Please go to lawyer directory to book from range of expert lawyers";
		String PATH_NULL_FOR_FILE_ERROR_MESSAGE = "File Path provided for generating Pre sigend URL is null";
		String EXPERTISE_NAME_NOT_EXIST_MESSAGE = "Expertise Name does not exist in database.";
		String ROLE_NOT_EXISTS_ERROR_MESSAGE = "Role does not exist in database";		
		String ENQUIRY_NOT_EXIST_MESSAGE = "Enquiry does not exist in database.";
		String COMPLAINT_NOT_EXIST_MESSAGE = "Complaint does not exist in database.";
		String ENTITY_NOT_EXIST_MESSAGE = "Entity does not exist in database.";
		String RESPONSE_NOT_RECIEVED = "don't get Response";
		String INVALID_ROLE_ERROR_MESSAGE = "User have invalid role";
		String REVIEW_NOT_EXIST_MESSAGE = "review does not exist in database.";
		String REVIEW_NOT_EXAUHSTED_MESSAGE = "Review Limit over";
		String EMAIL_CONTACT_ALREADY_EXIST_MESSAGE = "Email & Contact already exist, please login and then raise your query";
		String USER_SUPENEDED_MESSAGE = "User Suspended Temprory can't login";
		String ACCOUNT_NOT_APPROVED_MESSAGE = "Your Approval is pending from admin side";
		String USER_DELETED_MESSAGE = "User is deleted by EasyLaw Admin team, If you want to retrive your account connect with EasyLaw team"; 
		String USER_EXIST_IN_DATABASE_ERROR_MESSAGE = "User exist in database please go to login";
		String USER_EXIST_IN_DATABASE_ERROR_MESSAGES = "User exist in database please check";
		String ROLE_ASSIGNED_TO_USER="This role is assigned to an user, It cannot be deleted";
		
		
		
	
	}

	public interface FileLocation {

				 String PROPERTY_PATH = "file:///mnt/vedalegal/properties/application.properties";
//		 		 String PROPERTY_PATH = "file:///C:/Users/Ajay Agrawal/Desktop/Veda_Legal/src/main/resources/application.properties";
//				     String PROPERTY_PATH ="classpath:application.properties";
	}

}
