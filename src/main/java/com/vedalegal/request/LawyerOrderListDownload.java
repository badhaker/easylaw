package com.vedalegal.request;

import java.util.List;

import com.vedalegal.response.LawyerOrderList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawyerOrderListDownload {

	List<LawyerOrderList> OrderList;
}
