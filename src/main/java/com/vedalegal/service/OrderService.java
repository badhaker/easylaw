
package com.vedalegal.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.OrderEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.enums.LawyerBookingType;
import com.vedalegal.enums.OrderStatus;
import com.vedalegal.enums.OrderTypeEnum;
import com.vedalegal.enums.PlanType;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.exception.OrderNotFoundException;
import com.vedalegal.repository.OrderRepository;
import com.vedalegal.repository.SubServiceRepository;
import com.vedalegal.repository.UserRepository;
import com.vedalegal.request.AssignReq;
import com.vedalegal.request.BookLawyerRequest;
import com.vedalegal.request.OrderRequest;
import com.vedalegal.request.OrderVeda;
import com.vedalegal.request.Rating;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.LawyerMyOrder;
import com.vedalegal.response.LawyerOrderList;
import com.vedalegal.response.OrderDetailAdmin;
import com.vedalegal.response.OrderVedaListResponse;
import com.vedalegal.response.UserMyOrder;

@Service
public class OrderService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	OrderRepository orderRepo;

	@Autowired
	private EmailService emailService;
	@Autowired
	SubServiceRepository serviceRepo;

	public String addVedaOrder(OrderVeda order) throws AddressException, IOException, MessagingException {

		UserEntity user = userRepo.findById(order.getUserId())
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE));

		OrderEntity entity = OrderEntity.builder()
				// .dateOfOrder(new Date())
				.transactionId(order.getTransactionId()).userId(userRepo.findById(order.getUserId()).get())
				.subServiceId(serviceRepo.findById(order.getSubServiceId()).get()).planType(order.getPlanType())
				.orderNo(generateEasyLawOrderNumber(OrderTypeEnum.TP, order.getUserId(), order.getSubServiceId()))
				.status(OrderStatus.NEW).build();

		entity = orderRepo.save(entity);
		String emailBody = "Dear " + entity.getUserId().getName().split(" ")[0] + ", \n \t Your order for our service '"
				+ entity.getSubServiceId().getName() + ": " + entity.getPlanType()
				+ " plan' has been placed successfully. You will get a call from our team within 48 hours. \n\n Thanks and regards \n Easylaw  ";
		emailService.sendEmail(entity.getUserId().getEmail(), "Easylaw: Order placed successfully.", emailBody);

		return "Order saved successfully";
	}

	public List<OrderVedaListResponse> getVedaOrderList(OrderStatus status, String search) {

		List<OrderEntity> entityList = null;
		if (search == null || search.isEmpty()) {
			entityList = orderRepo.findAllByStatus(status);
		} else {
			entityList = orderRepo.findAllByStatusAndSerchValue(status, search);
		}
		if (entityList.size() <= 0) {
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE, AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		List<OrderVedaListResponse> respList = entityList.stream().filter(list -> list.getActive()!=false).map(order -> convertToRespList(order))
				.collect(Collectors.toList());

		return respList;
	}

	private OrderVedaListResponse convertToRespList(OrderEntity entity) {

		UserEntity assignedTo = entity.getAssignedTo();
		String assignTo = null;
		if (assignedTo != null)
			assignTo = assignedTo.getName();

		return OrderVedaListResponse.builder().id(entity.getId()).orderNo(entity.getOrderNo())
				.subServiceName(entity.getSubServiceId().getName()).userName(entity.getUserId().getName())
				.remarks(entity.getRemarks())
				.userEmail(entity.getUserId().getEmail()).userContactNo(entity.getUserId().getContactNo().toString())
				.assignedTo(assignTo).status(entity.getStatus())
				.userRemark(entity.getUserId().getUserremarks())
				.userRating(entity.getUserId().getUser_Rating()).build();
	}

	public OrderDetailAdmin getVedaOrderDetail(Long orderId) {
		OrderEntity entity = orderRepo.findById(orderId).orElse(null);
		if (entity == null || entity.getSubServiceId() == null) {
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE, AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		UserEntity assignedTo = entity.getAssignedTo();
		String assignTo = null;
		Long assignedToId = null;
		if (assignedTo != null) {
			assignTo = assignedTo.getName();
			assignedToId = assignedTo.getId();
		}
		return OrderDetailAdmin.builder().id(orderId).orderNo(entity.getOrderNo())
				.subServiceName(entity.getSubServiceId().getName()).userName(entity.getUserId().getName())
				.userEmail(entity.getUserId().getEmail()).userContactNo(entity.getUserId().getContactNo().toString())
				.assignedTo(assignTo).status(entity.getStatus()).remarks(entity.getRemarks())
				.transactionId(entity.getTransactionId()).assignedToId(assignedToId).build();
	}

	public CommonSuccessResponse bookLawyer(BookLawyerRequest order, String email)
			throws AddressException, IOException, MessagingException {

		UserEntity user = userRepo.findById(order.getUserId())
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE));

		UserEntity lawyer = userRepo.findById(order.getLawyerId())
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE));

		OrderEntity entity = OrderEntity.builder()
				// .dateOfOrder(new Date())
				.userId(userRepo.findById(order.getUserId()).get()).bookingType(order.getBookingType())
				.transactionId(order.getTransactionId()).expertise(order.getExpertise())
				.lawyerId(userRepo.findById(order.getLawyerId()).get()).status(OrderStatus.NEW)
				.orderNo(generateLawyerOrderNumber(OrderTypeEnum.TP, order.getUserId(), order.getLawyerId())).build();

		orderRepo.save(entity);
		if (order.getBookingType() == LawyerBookingType.InCall) {
			String emailBody = "Dear " + entity.getUserId().getName().split(" ")[0]
					+ ", \n \t Your order for our service '" + order.getExpertise() + ": " + order.getBookingType()
					+ "' has been placed successfully. You will get a 30 minutes call from our team within 48 hours. \n\n Thanks and regards \n Easylaw  ";
			emailService.sendEmail(entity.getUserId().getEmail(), "Easylaw: Order placed successfully.", emailBody);

			String emailBodys = "Dear " + entity.getLawyerId().getName().split(" ")[0] + ", \n \t An order for your service '"
					+ order.getExpertise() + ": " + order.getBookingType()
					+ "' has been placed successfully by"+entity.getUserId().getName().split(" ")[0]+"\\n \\t Contact number"+entity.getUserId().getContactNo()+". Kindly schedule a 30 minutes call within 48 hours. \n\n Thanks and regards \n Easylaw  ";
			emailService.sendEmail(entity.getLawyerId().getEmail(), "Easylaw: Order placed For Your Service.",
					emailBodys);

		}
		if (order.getBookingType() == LawyerBookingType.InPerson) {
			String emailBody = "Dear " + entity.getUserId().getName().split(" ")[0]
					+ ", \n \t Your order for our service '" + order.getExpertise() + ": " + order.getBookingType()
					+ "' has been placed successfully. You will get a call from our team within 48 hours. \n\n Thanks and regards \n Easylaw  ";
			emailService.sendEmail(entity.getUserId().getEmail(), "Easylaw: Order placed successfully.", emailBody);

			emailBody = "Dear " + entity.getLawyerId().getName().split(" ")[0] + ", \n \t An order for your service '"
					+ order.getExpertise() + ": " + order.getBookingType()
					+ "' has been placed successfully. Kindly connect with the Easy law team within 48 hours. \n\n Thanks and regards \n Easylaw  ";
			emailService.sendEmail(entity.getLawyerId().getEmail(), "Easylaw: Order placed For Your Service.",
					emailBody);

		}
		return new CommonSuccessResponse(true);
	}

	public List<LawyerOrderList> getLawyerOrderList(String search) {
		List<OrderEntity> entityList = null;
		if (search == null) {
			entityList = orderRepo.findLawyerOrderEntity();
		} else {
			entityList = orderRepo.findLawyerOrderEntityWithSearchValue(search);
		}

		if (entityList.size() <= 0) {
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE, AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		List<LawyerOrderList> respList = entityList.stream().filter(exp -> exp.getActive() != false)
				.map(order -> convertToOrderList(order)).collect(Collectors.toList());

		return respList;
	}

	private LawyerOrderList convertToOrderList(OrderEntity entity) {

		return LawyerOrderList.builder().id(entity.getId()).orderNo(entity.getOrderNo()).isActive(entity.getActive())
				.userName(entity.getUserId().getName()).userEmail(entity.getUserId().getEmail())
				.userContactNo(entity.getUserId().getContactNo().toString()).lawyerId(entity.getLawyerId().getId())
				.lawyerName(entity.getLawyerId().getName()).expertise(entity.getExpertise())
				.mode(entity.getBookingType()).status(entity.getStatus()).remarks(entity.getRemarks())
				.rating(entity.getRating()).userRemarks(entity.getUserRemarks()).build();
	}

	public List<LawyerMyOrder> getLawyerProfileOrder(Long id) {
		List<OrderEntity> myOrders = orderRepo.findAllByLawyer(id);
		System.out.println("Myorders count:" + myOrders.size());
		if (myOrders.size() <= 0) {
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE, AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		List<LawyerMyOrder> orders = myOrders.stream().map(o -> convertToMyOrder(o)).collect(Collectors.toList());
		return orders;
	}

	private LawyerMyOrder convertToMyOrder(OrderEntity entity) {
		return LawyerMyOrder.builder().id(entity.getId()).orderNo(entity.getOrderNo()).mode(entity.getBookingType())
				.userName(entity.getUserId().getName()).orderDate(entity.getCreated()).expertise(entity.getExpertise())
				.remarks(entity.getRemarks()).status(entity.getStatus()).build();

	}

	public List<UserMyOrder> getUserProfileOrder(Long id) {
		List<OrderEntity> myOrders = orderRepo.findAllByUser(id);
		System.out.println("Myorders count:" + myOrders.size());
		if (myOrders.size() <= 0) {
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE, AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		List<UserMyOrder> orders = myOrders.stream().map(o -> convertToUserOrder(o)).collect(Collectors.toList());
		return orders;
	}

	private UserMyOrder convertToUserOrder(OrderEntity entity) {

		String service = "";
		UserEntity LawyerId = entity.getLawyerId();
		String LawyerName = null;
		Long lawyerId = null;
		if (LawyerId != null) {
			LawyerName = LawyerId.getName();
			lawyerId = LawyerId.getId();
		}
		service = entity.getExpertise() == null ? entity.getSubServiceId().getName() : entity.getExpertise();
		return UserMyOrder.builder().id(entity.getId()).orderNo(entity.getOrderNo()).orderDate(entity.getCreated())
				.remarks(entity.getRemarks()).serviceName(service).status(entity.getStatus())
				.LawyerId(lawyerId)
				.LawyerName(LawyerName)
				.build();
	}

	private String generateLawyerOrderNumber(OrderTypeEnum orderType, Long userId, Long lawyerId) {

		LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String text = orderType + time.format(formatter) + "U" + userId + "L" + lawyerId;
		return text;
	}

	private String generateEasyLawOrderNumber(OrderTypeEnum orderType, Long userId, Long subServiceId) {

		LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String text = orderType + time.format(formatter) + "U" + userId + "S" + subServiceId;
		return text;
	}

	public String assignEasyLawAssociate(AssignReq order, Long orderId) throws AddressException, IOException, MessagingException {
		OrderEntity entity = orderRepo.findById(orderId).orElse(null);
		if (entity == null || entity.getSubServiceId() == null) {
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE, AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		UserEntity assignedTo = userRepo.findById(order.getAssociateId())
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE));

		entity.setAssignedTo(assignedTo);
		entity.setStatus(order.getOrderStatus());
		entity.setRemarks(order.getRemark());
		orderRepo.save(entity);
		String emailBody = "Dear "+ entity.getAssignedTo().getName().split(" ")[0]
				+ ", \n \t "+ entity.getOrderNo() +"order is Assign to you , Please connect with '"+entity.getUserId().getName()+"\n"
				+entity.getUserId().getContactNo()+ "'  \n\n Thanks and regards \n Easylaw  ";
		emailService.sendEmail(entity.getAssignedTo().getEmail(), "Easylaw: Order Assign successfully.", emailBody);

		String emailBodies = "Dear "+entity.getUserId().getName().split(" ")[0]
				+ ""+entity.getOrderNo()
				+", \n \t Your order is Assign to our Associate "+entity.getAssignedTo().getName()+" will connect in sometimes"
				+ "\n\n Thanks and regards \n Easylaw  ";
		emailService.sendEmail(entity.getUserId().getEmail(), "Easylaw: Order Assign successfully.", emailBodies);

		
		return "Order assigned to associate successfully";
	}

	public CommonSuccessResponse deleteLawyerOrder(Long id) {

		OrderEntity entity = orderRepo.findById(id).orElse(null);
		entity.setActive(false);
		orderRepo.save(entity);
		return new CommonSuccessResponse(true);
	}

	public String updateLawyerAssignAssociate(OrderRequest order, Long orderId) {
		OrderEntity entity = orderRepo.findById(orderId).orElse(null);
		entity.setStatus(order.getOrderStatus());
		entity.setRemarks(order.getRemark());
		orderRepo.save(entity);

		return "Lawyer Order Update successfully";
	}

	public Rating ratingAverage(Rating order) {
		List<OrderEntity> entity = orderRepo.findByLawyerIddata(order.getLawyerId());
		System.out.println("In RatingAverage Service");
		System.out.println("Entity Size:"+entity.size());
		if (entity.size()>0) {
			Long rating = 0l;
			long size = entity.size();
			for (OrderEntity e : entity) {
				rating += e.getRating();
			}

			Long averageRating = (rating / size);
			System.out.println("averageRating---" + averageRating);
			
			userRepo.findByLawyerUpdate(order.getLawyerId(), averageRating);

		}
		return order;

	}
}
