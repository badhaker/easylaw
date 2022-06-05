package com.vedalegal.response;

import java.util.List;

import com.vedalegal.modal.CategoryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopServicesGet {

	//private Long masterServiceId;
	//private String masterServiceName;
	Long masterServiceId;
	String masterServiceName;
	List<CategoryResponse> categories;
	String imgUrl;
	String description;
}
