package com.vedalegal.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsGetList {

	private Long id;
	private String headline;
	private Date createdAt;
	private boolean isActive;
	private Date updatedDate;
}
