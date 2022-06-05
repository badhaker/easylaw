package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MasterServiceResponse {

	Long masterServiceId;
	String masterService;
	String category1;
	String category2;
	String category3;
	String category4;
	String imgUrl;
	String description;
	

	
}
