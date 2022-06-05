package com.vedalegal.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.ComplaintEntity;
import com.vedalegal.entity.OrderEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.enums.ComplaintStatusEnum;
import com.vedalegal.exception.ComplaintNotFoundException;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.exception.OrderNotFoundException;
import com.vedalegal.repository.ComplaintRepository;
import com.vedalegal.repository.OrderRepository;
import com.vedalegal.repository.UserRepository;
import com.vedalegal.request.Complaint;
import com.vedalegal.request.UpdateComplaintRequest;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ComplaintList;

@Service
public class ComplaintService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ComplaintRepository complaintRepo;;
	
	public CommonSuccessResponse addComplaint(Complaint complaint) {
		OrderEntity order=orderRepo.findByOrderNo(complaint.getOrderNo());
		if(order==null)
		{
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		UserEntity user=userRepo.findById(complaint.getUserId()).orElse(null);
		if(user==null)
		{
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.USER_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE);
		}
	
		ComplaintEntity entity=
				ComplaintEntity.builder()
				.complaintNo(generateComplaintNo( order.getId(),complaint.getUserId()))
				.complaintDescription(complaint.getComplaintDescription())
				.orderId(order)
				.serviceName(complaint.getServiceName())
				.status(ComplaintStatusEnum.NEW)
				.userId(user)
				.build();
		
		complaintRepo.save(entity);
		
		return new  CommonSuccessResponse(true);
		
	}

	public List<ComplaintList> getComplaintList(ComplaintStatusEnum status, int page, int limit, String search) {
		if (page>0) 
			page=page-1;

		Pageable pageable=PageRequest.of(page, limit,Direction.DESC,"id");
		List<ComplaintEntity> complaint=null;
		if (search==null||search.isEmpty()) {
			complaint= complaintRepo.findAllByStatus(status,pageable);
		}else {
			complaint= complaintRepo.findAllByStatusWithSearch(status,search,pageable);
		}
		
		List<ComplaintList>  complaintList=complaint.stream().filter(list -> list.getActive()==true).map(comp->convertToList(comp)).collect(Collectors.toList());
		if(complaintList.size()<=0)
		{
			throw new ComplaintNotFoundException(AppConstant.ErrorTypes.COMPLAINT_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.COMPLAINT_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.COMPLAINT_NOT_EXIST_MESSAGE);
		}
		return complaintList;
	}

	private ComplaintList convertToList(ComplaintEntity entity) {
		// TODO Auto-generated method stub
		return ComplaintList.builder()
				.id(entity.getId())
				.complaintNo(entity.getComplaintNo())
				.orderNo(entity.getOrderId().getOrderNo())
				.complaintDescription(entity.getComplaintDescription())
				.status(entity.getStatus())
				.remarks(entity.getRemarks())
				.userName(entity.getUserId().getName())
				.userEmail(entity.getUserId().getEmail())
				.UserContact(entity.getUserId().getContactNo())
				.serviceName(entity.getServiceName())
				.dateOfComplaint(entity.getCreated())
				.build();
	}
	
	public String generateComplaintNo(Long orderId,Long userId)
	{
		LocalDateTime time=LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String text="C"+time.format(formatter)+"U"+userId+"O"+orderId;
		return text;
	}

	public CommonSuccessResponse updateComplaint(UpdateComplaintRequest updateComplaintRequest) {
		// TODO Auto-generated method stub
		
		ComplaintEntity entity= complaintRepo.findById(updateComplaintRequest.getComplaintId()).orElseThrow(()->
				new ComplaintNotFoundException(AppConstant.ErrorTypes.COMPLAINT_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.COMPLAINT_NOT_EXIST_CODE,
						AppConstant.ErrorMessage.COMPLAINT_NOT_EXIST_MESSAGE));
		
		String remark=updateComplaintRequest.getRemark();
		ComplaintStatusEnum status=updateComplaintRequest.getStatus();
		if (remark!=null) {
			entity.setRemarks(remark);
		}
		if (status!=null) {
			entity.setStatus(status);
		}
		complaintRepo.save(entity);
		return new CommonSuccessResponse(true);
	}

	public String deleteComplaint(Long complaintId)
	{
		Optional<ComplaintEntity> complaintEntityOptional = complaintRepo.findById(complaintId);
		ComplaintEntity complaintEntity = complaintEntityOptional.get();
		complaintEntity.setActive(false);
		complaintRepo.save(complaintEntity);
//		complaintRepo.deleteById(complaintId);
		return "Complaint deleted successfully";
	}

}
