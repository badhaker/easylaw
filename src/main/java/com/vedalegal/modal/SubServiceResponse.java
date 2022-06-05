package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubServiceResponse {

	public Long id;
	public String subService;
	public String masterSercice;
	public String category;

	public SubServiceResponse() {
	}

	public SubServiceResponse(Long id, String subService, String masterSercice, String category) {
		this.id = id;
		this.subService = subService;
		this.masterSercice = masterSercice;
		this.category = category;
	}
}
