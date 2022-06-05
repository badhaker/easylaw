
package com.vedalegal.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.enums.OrderStatus;
import com.vedalegal.request.AssignReq;
import com.vedalegal.request.BookLawyerRequest;
import com.vedalegal.request.LawyerOrderListDownload;
import com.vedalegal.request.NewOrderListDownload;
import com.vedalegal.request.OrderRequest;
import com.vedalegal.request.OrderVeda;
import com.vedalegal.request.Rating;
import com.vedalegal.resource.SecurityConstants;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.LawyerMyOrder;
import com.vedalegal.response.LawyerOrderList;
import com.vedalegal.response.OrderDetailAdmin;
import com.vedalegal.response.OrderVedaListResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.response.UserMyOrder;
import com.vedalegal.security.JwtTokenProvider;
import com.vedalegal.service.EasyLawNewOrderExcelExporter;
import com.vedalegal.service.LawyerOrderExcelExporter;
import com.vedalegal.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/addEasyLawOrder")
	public ResponseEntity<BaseApiResponse> addVedaOrder(@RequestBody OrderVeda order) throws AddressException, IOException, MessagingException
	{
		String msg=orderService.addVedaOrder(order);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getEasyLawOrderList")
	public ResponseEntity<BaseApiResponse> getVedaOrderList(@RequestParam OrderStatus status,
			@RequestParam(required = false) String search) 
	{
		List<OrderVedaListResponse> list=orderService.getVedaOrderList(status ,search);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	@GetMapping("/getEasyLawOrderDetail/{id}")
	public ResponseEntity<BaseApiResponse> getVedaOrderDetail(@PathVariable Long id) 
	{
		OrderDetailAdmin orderDetail=orderService.getVedaOrderDetail(id );
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(orderDetail);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	} 
	
	@PutMapping("/assignAssociate/{orderId}")
	public ResponseEntity<BaseApiResponse> assignEasyLawAssociate(@RequestBody AssignReq order, @PathVariable Long orderId)
			throws AddressException, IOException, MessagingException {
		String msg=orderService.assignEasyLawAssociate(order,orderId );
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	@PostMapping("/bookLawyer")
	  public ResponseEntity<BaseApiResponse> bookLawyer(@RequestBody BookLawyerRequest bookLawyerRequest, 
			@RequestHeader(value = SecurityConstants.SecretKey.TOKEN_HEADER) String token) throws AddressException, IOException, MessagingException{
		  	String email=jwtTokenProvider.extractUserEmail(token.substring(7));
		  	CommonSuccessResponse response=orderService.bookLawyer(bookLawyerRequest,email);
			BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
			return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
			
		}

	@GetMapping("/getLawyerOrderList")
	public ResponseEntity<BaseApiResponse> getLawyerOrderList(@RequestParam(required = false) String search) 
	{
		List<LawyerOrderList> list=orderService.getLawyerOrderList(search);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("lawyer/myOrders/{id}")
	public ResponseEntity<BaseApiResponse> getLawyerProfileOrder(@PathVariable Long id) 
	{
		List<LawyerMyOrder> orderDetail=orderService.getLawyerProfileOrder(id );
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(orderDetail);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("user/myOrders/{id}")
	public ResponseEntity<BaseApiResponse> getUserProfileOrder(@PathVariable Long id) 
	{
		List<UserMyOrder> orderDetail=orderService.getUserProfileOrder(id );
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(orderDetail);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PostMapping("/easyLawOrder/exportToExcel")
	public void downloadOrderFile(@RequestBody NewOrderListDownload orderList, HttpServletResponse response) throws IOException
	{
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=orders_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<OrderVedaListResponse> orders = orderList.getOrderList();

		EasyLawNewOrderExcelExporter excelExporter = new EasyLawNewOrderExcelExporter(orders);
		excelExporter.export(response);    
			
	}
	@GetMapping("/lawyerOrder/exportToExcel")
	public void downloadLawyerOrderFile(@RequestBody LawyerOrderListDownload orderList, HttpServletResponse response) throws IOException
	{
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=orders_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<LawyerOrderList> orders = orderList.getOrderList();

		LawyerOrderExcelExporter excelExporter = new LawyerOrderExcelExporter(orders);
		excelExporter.export(response);    
			
	}
	
	@DeleteMapping("/deleteLawyerOrder")
	  public ResponseEntity<BaseApiResponse> deleteLawyerOrder(@RequestParam Long id){

		  	CommonSuccessResponse response=orderService.deleteLawyerOrder(id);
			BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
			return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
			
		}
	@PutMapping("/updateLawyerOrder/{orderId}")
	public ResponseEntity<BaseApiResponse> updateLawyerAssignAssociate(@RequestBody OrderRequest order, @PathVariable Long orderId)
	{
		String msg=orderService.updateLawyerAssignAssociate(order,orderId );
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PutMapping("/ratingAverage")
	public ResponseEntity<BaseApiResponse> ratingAverage(@RequestBody Rating order) {
		Rating avrRating = orderService.ratingAverage(order);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(avrRating);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
} 
	

