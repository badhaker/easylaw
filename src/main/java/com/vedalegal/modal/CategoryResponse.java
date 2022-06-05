package com.vedalegal.modal;

import java.util.List;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CategoryResponse {

	private Long id;
	private String name;
	private List<SubServiceWebResponse> subServices;
}
