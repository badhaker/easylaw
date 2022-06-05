package com.vedalegal.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQDTO 
{
	private Long id;
	private String question;
	private String answer;
	private Boolean active;
}
