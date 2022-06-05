package com.vedalegal.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	private Long id;
	private String name;
	//private int status;
	//private MasterService masterService;
	// private List<SubService> subService;

}
