package com.vedalegal.modal;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MasterServiceDetailResponse {

	Long masterServiceId;
	String masterService;
	List<CategoryResponse> categories;
	String imgUrl;
	String description;
}
