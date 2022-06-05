package com.vedalegal.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vedalegal.exception.AppException;
import com.vedalegal.exception.AreaOfExpertiseNameException;
import com.vedalegal.exception.CategoryNotFoundException;
import com.vedalegal.exception.ComplaintNotFoundException;
import com.vedalegal.exception.EnquiryNotFoundException;
import com.vedalegal.exception.FileNotUploadedException;
import com.vedalegal.exception.InvalidInputException;
import com.vedalegal.exception.InvalidOtpException;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.exception.MasterServiceNotExistException;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.exception.OrderNotFoundException;
import com.vedalegal.exception.PermissionNotExistException;
import com.vedalegal.exception.RoleAlreadyExistException;
import com.vedalegal.exception.RoleNotExistException;
import com.vedalegal.exception.SubServiceNotExistException;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseStatus;

@ControllerAdvice
public class UniversalControllerAdvice extends ResponseEntityExceptionHandler {
	
	/**
	 * For Handling MethodArgumentNotValidException
	 **/
	/*@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> handleEmptyResultDataAccess(EmptyResultDataAccessException e)
	{
		return new ResponseEntity<String>("Role with given id does'nt exist in database", HttpStatus.BAD_REQUEST);MethodArgumentNotValidException
	}
	
	/*@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<FieldError> allBindingErrorsList = ex.getBindingResult().getFieldErrors();
		List<String> allBindingErrorsMessageList = new ArrayList<String>();

		for (FieldError error : allBindingErrorsList) {
			allBindingErrorsMessageList.add(error.getField() + " - " + error.getDefaultMessage());
		}

		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));

		InvalidInputException invalidInputException = new InvalidInputException(
				AppConstant.ErrorTypes.INVALID_INPUT_ERROR, AppConstant.ErrorCodes.INVALID_INPUT_ERROR_CODE,
				allBindingErrorsMessageList.toString());

		baseApiResponse.setResponseData(invalidInputException);

		return new ResponseEntity<Object>(baseApiResponse, HttpStatus.OK);
	}*/
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseApiResponse> exception(
			Exception appException, HttpServletRequest request) {
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(appException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(AppException.class)
	public ResponseEntity<BaseApiResponse> appException(
			AppException appException, HttpServletRequest request) {
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(appException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<BaseApiResponse> validationException(
//			MethodArgumentNotValidException exception, HttpServletRequest request) {
//		BaseApiResponse baseApiResponse = new BaseApiResponse();
//		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
//		baseApiResponse.setResponseData(exception);
//		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
//	}
//	
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<BaseApiResponse> categoryNotFoundException(
			CategoryNotFoundException categoryNotFoundException, HttpServletRequest request) {
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(categoryNotFoundException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(MasterServiceNotExistException.class)
	public ResponseEntity<BaseApiResponse> masterServiceNotExistException(
			MasterServiceNotExistException masterServiceNotExistException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(masterServiceNotExistException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<BaseApiResponse> invalidInputException(
			InvalidInputException invalidInputException, HttpServletRequest request) {
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(invalidInputException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(InvalidOtpException.class)
	public ResponseEntity<BaseApiResponse> invalidOtpException(InvalidOtpException invalidOtpException,
			HttpServletRequest request) {
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(invalidOtpException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(PermissionNotExistException.class)
	public ResponseEntity<BaseApiResponse> permissionNotExistException(
			PermissionNotExistException permissionNotExistException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(permissionNotExistException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(RoleNotExistException.class)
	public ResponseEntity<BaseApiResponse> roleNotExistException(
			RoleNotExistException roleNotExistException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(roleNotExistException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(SubServiceNotExistException.class)
	public ResponseEntity<BaseApiResponse> subServiceNotExistException(
			SubServiceNotExistException subServiceNotExistException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(subServiceNotExistException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(FileNotUploadedException.class)
	public ResponseEntity<BaseApiResponse> fileNotUploadedException(
			FileNotUploadedException fileNotUploadedException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(fileNotUploadedException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	@ExceptionHandler(LawyerOrUserNotFoundException.class)
	public ResponseEntity<BaseApiResponse> fileNotUploadedException(
			LawyerOrUserNotFoundException lawyerNotUploadedException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(lawyerNotUploadedException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	@ExceptionHandler(AreaOfExpertiseNameException.class)
	public ResponseEntity<BaseApiResponse> fileNotUploadedException(
			AreaOfExpertiseNameException areaOfExpertiseNameException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(areaOfExpertiseNameException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<BaseApiResponse> fileNotUploadedException(
			OrderNotFoundException orderNotFoundException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(orderNotFoundException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(EnquiryNotFoundException.class)
	public ResponseEntity<BaseApiResponse> fileNotUploadedException(
			EnquiryNotFoundException enquiryNotFoundException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(enquiryNotFoundException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(ComplaintNotFoundException.class)
	public ResponseEntity<BaseApiResponse> complaintNotFoundException(
			ComplaintNotFoundException complaintNotFoundException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(complaintNotFoundException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(RoleAlreadyExistException.class)
	public ResponseEntity<BaseApiResponse> complaintNotFoundException(
			RoleAlreadyExistException roleAlreadyExistException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(roleAlreadyExistException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(NoEntityInDatabaseException.class)
	public ResponseEntity<BaseApiResponse> complaintNotFoundException(
			NoEntityInDatabaseException noEntityInDatabaseException, HttpServletRequest request) 
	{
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCodes.FAILURE));
		baseApiResponse.setResponseData(noEntityInDatabaseException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
