package com.vedalegal.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceDTO {

	private Long id;
	private String subService;
	public String masterSercice;
	public String category;
	
	
}
