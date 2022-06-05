package com.vedalegal.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BannerModal {
	
	private Long id;

	private String bannerUrl;
	
	private Long serviceId;
	
	private String hyperLink;
	
	private boolean isActive;
}
