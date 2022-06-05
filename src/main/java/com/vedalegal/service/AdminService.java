package com.vedalegal.service;

import com.vedalegal.enums.AdminDashboardFilterEnum;
import com.vedalegal.resource.AdminDashboardDataResponse;
import com.vedalegal.response.LawyerOrdersResponse;

public interface AdminService {

	LawyerOrdersResponse getOrderList(int page, int limit);

	AdminDashboardDataResponse getAdminDashboardData(AdminDashboardFilterEnum filterType);

}
