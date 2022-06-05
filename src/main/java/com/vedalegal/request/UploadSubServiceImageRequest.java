package com.vedalegal.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadSubServiceImageRequest {
	
	private Long subServiceId;
	private String imageDescription;
	private String type;
	private Long subServiceImgId;

	private MultipartFile subServiceImage;
}
