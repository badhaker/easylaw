package com.vedalegal.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QusAns {
	
	Long qusAnsId;
	String question;
	String answer;
}
  