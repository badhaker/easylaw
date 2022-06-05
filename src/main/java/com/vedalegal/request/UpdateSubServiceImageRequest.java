package com.vedalegal.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubServiceImageRequest {
	
	private Long id;
	private String imageDescription;
	private String type;
	private Long subServiceId;

}
