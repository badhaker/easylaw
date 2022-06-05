package com.vedalegal.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboardDataResponse {
	private Long easyLawOrders;
	private Long lawyerOrders;
	private Long newUser;
	private Long newLawyer;
	private Long newEnquiries;
	private Long newComplaints;
}
