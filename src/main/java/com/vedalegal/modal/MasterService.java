package com.vedalegal.modal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MasterService {

	private Long id;
	private String name;
	private String description;
	private String image;
	private List<Category> categories;
	
}
