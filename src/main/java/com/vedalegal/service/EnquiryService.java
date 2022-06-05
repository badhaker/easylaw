package com.vedalegal.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.hibernate.query.internal.AbstractProducedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.BannerEntity;
import com.vedalegal.entity.EnquiryEntity;
import com.vedalegal.entity.OrderEntity;
import com.vedalegal.entity.SubServiceEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.enums.EnquiryStatus;
import com.vedalegal.enums.OrderTypeEnum;
import com.vedalegal.exception.EnquiryNotFoundException;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.exception.OrderNotFoundException;
import com.vedalegal.modal.GetEnquiryDetailsResponse;
import com.vedalegal.modal.SendEnquiry;
import com.vedalegal.repository.EnquiryRepository;
import com.vedalegal.repository.SubServiceRepository;
import com.vedalegal.repository.UserRepository;
import com.vedalegal.request.AssignAssociateReq;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.EnquiryList;

@Service
public class EnquiryService {

	@Autowired
	private EnquiryRepository enquiryRepository;

	@Autowired
	private SubServiceRepository subServiceRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailService emailService;

	public CommonSuccessResponse sendLoginEnquiry(SendEnquiry sendEnquiry) {

		EnquiryEntity entity = new EnquiryEntity();

		if (sendEnquiry.getServiceId() != null && sendEnquiry.getServiceId() != 0) {
			SubServiceEntity role = subServiceRepository.findById(sendEnquiry.serviceId).orElse(null);
			entity.setSubServiceNo(role);
		}
		entity.setUserName(sendEnquiry.getName());
		entity.setEmail(sendEnquiry.getEmail());
		entity.setContactNo(sendEnquiry.getContactNo());
		entity.setQuery(sendEnquiry.getEnquiry());
		entity.setStatus(EnquiryStatus.NEW);
		// entity.setDateOfEnquiry(new Date());
		// entity.setStatus("Pending");
		String enquiryNo = null;
		if (sendEnquiry.getServiceId() != null && sendEnquiry.getServiceId() != 0) {
			enquiryNo = generateEnquiryNumber(entity.getId(), entity.getSubServiceNo().getId());
		} else {
			enquiryNo = generateEnquiryNumber(entity.getId(), 0L);
		}
		entity = enquiryRepository.save(entity);
		entity.setEnquiryNo(enquiryNo);
		enquiryRepository.save(entity);

		String supportEmail = "easylaw.web@gmail.com";
		String supportEmailBody = "Hey " + sendEnquiry.getEnquiry() + " has raised a query please connect with "
				+ sendEnquiry.getEnquiry();
		String emailBody = "Dear " + sendEnquiry.getName()
				+ " Your Query has been Saved Successfully Our Executive will connect you in sometime";
		try {
			emailService.sendEmail(supportEmail, "New Query", supportEmailBody);
			emailService.sendEmail(sendEnquiry.getEmail(), "Easylaw: Query Send Successfully.", emailBody);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
		return new CommonSuccessResponse(true);

	}

	public CommonSuccessResponse sendEnquiry(SendEnquiry sendEnquiry) {

		EnquiryEntity entity = new EnquiryEntity();

		if (userRepo.existsByEmail(sendEnquiry.getEmail())) {
			if (userRepo.existsBycontactNo(sendEnquiry.getContactNo())) {
				throw new EnquiryNotFoundException(AppConstant.ErrorTypes.EMAIL_CONTACT_ALREADY_EXIST,
						AppConstant.ErrorCodes.EMAIL_CONTACT_ALREADY_EXIST_CODE,
						AppConstant.ErrorMessage.EMAIL_CONTACT_ALREADY_EXIST_MESSAGE);
			}
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.EMAIL_CONTACT_ALREADY_EXIST,
					AppConstant.ErrorCodes.EMAIL_CONTACT_ALREADY_EXIST_CODE,
					AppConstant.ErrorMessage.EMAIL_CONTACT_ALREADY_EXIST_MESSAGE);
		}

		if (sendEnquiry.getServiceId() != null && sendEnquiry.getServiceId() != 0) {
			SubServiceEntity role = subServiceRepository.findById(sendEnquiry.serviceId).orElse(null);
			entity.setSubServiceNo(role);
		}
		entity.setUserName(sendEnquiry.getName());
		entity.setEmail(sendEnquiry.getEmail());
		entity.setContactNo(sendEnquiry.getContactNo());
		entity.setQuery(sendEnquiry.getEnquiry());
		entity.setStatus(EnquiryStatus.NEW);
		// entity.setDateOfEnquiry(new Date());
		// entity.setStatus("Pending");

		entity = enquiryRepository.save(entity);
		String enquiryNo = null;
		if (sendEnquiry.getServiceId() != null && sendEnquiry.getServiceId() != 0) {
			enquiryNo = generateEnquiryNumber(entity.getId(), entity.getSubServiceNo().getId());
		} else {
			enquiryNo = generateEnquiryNumber(entity.getId(), 0L);
		}
		entity.setEnquiryNo(enquiryNo);
		enquiryRepository.save(entity);

		String supportEmail = "easylaw.web@gmail.com";
		String supportEmailBody = "Hey " + sendEnquiry.getEnquiry() + " has raised a query please connect with "
				+ sendEnquiry.getEnquiry();
		String emailBody = "Dear " + sendEnquiry.getName()
				+ " Your Query has been Saved Successfully Our Executive will connect you in sometime";
		try {
			emailService.sendEmail(supportEmail, "New Query", supportEmailBody);
			emailService.sendEmail(sendEnquiry.getEmail(), "Easylaw: Query Send Successfully.", emailBody);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
		return new CommonSuccessResponse(true);

	}

	private String generateEnquiryNumber(Long enquiryId, Long subServiceId) {

		LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String text = time.format(formatter) + "E" + enquiryId + "S" + subServiceId;
		return text;
	}

	public GetEnquiryDetailsResponse getEnquiryDetails(Long id) {
		EnquiryEntity entity = enquiryRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENQUIRY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENQUIRY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENQUIRY_NOT_EXIST_MESSAGE);

		}
		GetEnquiryDetailsResponse up = convertToEnquiryDetails(entity);
		return up;
	}

	private GetEnquiryDetailsResponse convertToEnquiryDetails(EnquiryEntity entity) {

		String assigndTo = null;
		Long assigndToId = null;
		if (entity.getAssignedTo() != null) {
			assigndTo = entity.getAssignedTo().getName();
			assigndToId = entity.getAssignedTo().getId();
		}
		String service = null;
		Long serviceId = null;
		if (entity.getSubServiceNo() != null) {
			service = entity.getSubServiceNo().getName();
			serviceId = entity.getSubServiceNo().getId();
		}
		return GetEnquiryDetailsResponse.builder().id(entity.getId()).Username(entity.getUserName())
				.contactNo(entity.getContactNo()).email(entity.getEmail()).enquiry(entity.getQuery())
				.servicename(service).Date(entity.getCreated()).status(entity.getStatus()).remark(entity.getRemarks())
				.AssignedTo(assigndTo).assigndToId(assigndToId).serviceToId(serviceId).build();
	}

	public List<EnquiryList> getServiceList(int page, int limit, String search, EnquiryStatus status) {

		if (page > 0)
			page = page - 1;

		Pageable pageable = PageRequest.of(page, limit, Direction.DESC, "id");
		List<EnquiryEntity> list = null;
		if (search == null || search.isEmpty()) {
			list = enquiryRepository.findAllEnqueryList(status);
		} else {
			list = enquiryRepository.findAllEnqueryListWithSearch(search, status);
			List<EnquiryEntity> list1 = enquiryRepository.findAllEnqueryListAssignedWithSearch(search, status);
			list.addAll(list1);
		}

		if (list.size() <= 0) {
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENQUIRY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENQUIRY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENQUIRY_NOT_EXIST_MESSAGE);
		}
		List<EnquiryList> listResp = list.stream().filter(l -> l.getActive() == true).map(enq -> convertToModal(enq))
				.collect(Collectors.toList());
		return listResp;
	}

	public List<EnquiryList> getServiceList(String search, EnquiryStatus status) {
		List<EnquiryEntity> list = null;
		if (search == null || search.isEmpty()) {
			list = enquiryRepository.findAllEnqueryList(status);
		} else {
			list = enquiryRepository.findAllEnqueryListWithSearch(search, status);
			List<EnquiryEntity> list1 = enquiryRepository.findAllEnqueryListAssignedWithSearch(search, status);
			list.addAll(list1);
		}

		if (list.size() <= 0) {
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENQUIRY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENQUIRY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENQUIRY_NOT_EXIST_MESSAGE);
		}
		List<EnquiryList> listResp = list.stream().filter(ll -> ll.getActive()).map(enq -> convertToModal(enq))
				.collect(Collectors.toList());
		return listResp;
	}

	public EnquiryList convertToModal(EnquiryEntity enq) {
		String service = null;
		if (enq.getSubServiceNo() != null)
			service = enq.getSubServiceNo().getName();
		String assigndTo = null;
		if (enq.getAssignedTo() != null)
			assigndTo = enq.getAssignedTo().getName();

		return EnquiryList.builder().id(enq.getId()).subServiceName(service).assignedTo(assigndTo)
				.raisedOn(enq.getCreated()).userName(enq.getUserName()).status(enq.getStatus())
				.remarks(enq.getRemarks()).email(enq.getEmail()).query(enq.getQuery()).enquiryNo(enq.getEnquiryNo())
				.contactNo(enq.getContactNo()).build();
	}

	public String assignEasyLawAssociate(AssignAssociateReq enq, Long enquiryId) {
		EnquiryEntity entity = enquiryRepository.findById(enquiryId).orElse(null);
		if (entity == null) {
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENQUIRY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENQUIRY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENQUIRY_NOT_EXIST_MESSAGE);
		}
		UserEntity assignedTo = userRepo.findById(enq.getAssociateId())
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE));

		entity.setAssignedTo(assignedTo);
		entity.setStatus(enq.getEnquiryStatus());
		entity.setRemarks(enq.getRemark());
		enquiryRepository.save(entity);

		return "Enquiry assigned to associate successfully";
	}

	public List<EnquiryList> getUserServiceList(String email) {
		List<EnquiryEntity> list = enquiryRepository.findByEmail(email);

		List<EnquiryList> listResp = list.stream().filter(l -> l.getActive()).map(enq -> convertToModal1(enq))
				.collect(Collectors.toList());
		return listResp;
	}

	public EnquiryList convertToModal1(EnquiryEntity enq) {

		return EnquiryList.builder()
	  			.id(enq.getId())
				.raisedOn(enq.getCreated()).userName(enq.getUserName()).status(enq.getStatus())
				.remarks(enq.getRemarks()).email(enq.getEmail()).query(enq.getQuery()).enquiryNo(enq.getEnquiryNo())
				.contactNo(enq.getContactNo()).isactive(enq.getActive()).build();
	}

	public String deleteEnquiry(Long id) {
		EnquiryEntity entity = enquiryRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		if (entity.getActive().equals(true)) {
			entity.setActive(false);
		}
		enquiryRepository.save(entity);
		return "Enquiry deleted successfully";
	}

	public CommonSuccessResponse sendContactEnquiry(SendEnquiry sendEnquiry) {

		EnquiryEntity entity = new EnquiryEntity();

		if (sendEnquiry.getServiceId() != null && sendEnquiry.getServiceId() != 0) {
			SubServiceEntity role = subServiceRepository.findById(sendEnquiry.serviceId).orElse(null);
			entity.setSubServiceNo(role);
		}
		entity.setUserName(sendEnquiry.getName());
		entity.setEmail(sendEnquiry.getEmail());
		entity.setContactNo(sendEnquiry.getContactNo());
		entity.setQuery(sendEnquiry.getEnquiry());
		entity.setStatus(EnquiryStatus.NEW);
		// entity.setDateOfEnquiry(new Date());
		// entity.setStatus("Pending");
		String enquiryNo = null;

		entity = enquiryRepository.save(entity);

		if (sendEnquiry.getServiceId() != null && sendEnquiry.getServiceId() != 0) {
			enquiryNo = generateEnquiryNumber(entity.getId(), entity.getSubServiceNo().getId());
		} else {
			enquiryNo = generateEnquiryNumber(entity.getId(), 0L);
		}
		entity.setEnquiryNo(enquiryNo);
		enquiryRepository.save(entity);

		String supportEmail = "easylaw.web@gmail.com";
		String supportEmailBody = "Hey " + sendEnquiry.getEnquiry() + " has raised a query please connect with "
				+ sendEnquiry.getEnquiry();
		String emailBody = "Dear " + sendEnquiry.getName()
				+ " Your Query has been Saved Successfully Our Executive will connect you in sometime";
		try {
			emailService.sendEmail(supportEmail, "New Query", supportEmailBody);
			emailService.sendEmail(sendEnquiry.getEmail(), "Easylaw: Query Send Successfully.", emailBody);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
		return new CommonSuccessResponse(true);

	}
}
