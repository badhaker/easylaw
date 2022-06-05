package com.vedalegal.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.RoleEntity;
import com.vedalegal.enums.AdminDashboardFilterEnum;
import com.vedalegal.exception.AppException;
import com.vedalegal.repository.ComplaintRepository;
import com.vedalegal.repository.EnquiryRepository;
import com.vedalegal.repository.OrderRepository;
import com.vedalegal.repository.RoleRepository;
import com.vedalegal.repository.UserRepository;
import com.vedalegal.resource.AdminDashboardDataResponse;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.LawyerOrdersResponse;
import com.vedalegal.service.AdminService;

@Service
public class AdminServiceImp implements AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EnquiryRepository enquiryRepository;
	
	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Override
	public LawyerOrdersResponse getOrderList(int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdminDashboardDataResponse getAdminDashboardData(AdminDashboardFilterEnum filterType) {
		AdminDashboardDataResponse response = new AdminDashboardDataResponse();
		
		LocalDate localDate=LocalDate.now(ZoneId.of("Asia/Kolkata"));
		Date utilDate=null;
		if (AdminDashboardFilterEnum.Month.equals(filterType)) {
			
			localDate=localDate.minusMonths(1);
		}else if (AdminDashboardFilterEnum.Week.equals(filterType)) {
			localDate=localDate.minusWeeks(1);
		}else if (AdminDashboardFilterEnum.Year.equals(filterType)) {
			localDate=localDate.minusYears(1);
		}
		utilDate=Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		
		RoleEntity userRole= roleRepository.findByName(AppConstant.Commons.USER_ROLE).orElseThrow(()->
					new AppException(AppConstant.ErrorTypes.ROLE_NOT_EXISTS,
							AppConstant.ErrorCodes.ROLE_NOT_EXISTS_ERROR_CODE,
							AppConstant.ErrorMessage.ROLE_NOT_EXISTS_ERROR_MESSAGE));
		
		RoleEntity lyawerRole= roleRepository.findByName(AppConstant.Commons.LAWYER_ROLE).orElseThrow(()->
					new AppException(AppConstant.ErrorTypes.ROLE_NOT_EXISTS,
							AppConstant.ErrorCodes.ROLE_NOT_EXISTS_ERROR_CODE,
							AppConstant.ErrorMessage.ROLE_NOT_EXISTS_ERROR_MESSAGE));
		
		Long userCount=userRepository.getCountByRoleAndCreatedDate(userRole,utilDate);
		Long lyawerCount=userRepository.getCountByRoleAndCreatedDate(lyawerRole,utilDate);

		Long lyawerOrderCount=orderRepository.getLowerOrderCountByCreatedDate(utilDate);
		
		Long easstLawOrderCount=orderRepository.getServiceOrderCountByCreatedDate(utilDate);
		
		Long enquiryCount=enquiryRepository.getEnqueryCountByCreatedDate(utilDate);
		
		Long complaintCount= complaintRepository.getComplaintCountByCreatedDate(utilDate);
		
		response.setNewLawyer(lyawerCount);
		response.setNewUser(userCount);
		response.setEasyLawOrders(easstLawOrderCount);
		response.setLawyerOrders(lyawerOrderCount);
		response.setNewEnquiries(enquiryCount);
		response.setNewComplaints(complaintCount);
		return response;
	}


}
