package com.vedalegal.request;

import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleMeeting {

	@NotNull(message = "CandidateId cannot be null or blank.")
	@DecimalMin(message = "Minimum value for candidateId must be greater than zero.", value = "1")
	private Long clientId;
	
	@NotNull(message = "JobProfileId cannot be null or blank.")
	@DecimalMin(message = "Minimum value for lawyerId must be greater than zero.", value = "1")
	private Long lawyerId;

	private String meetingDate;
	
	@NotBlank(message = "Please provide meeting start time in hh:mm a Format(Ex: 04:40 PM)")
	@NotNull(message = "Please provide meeting start time in hh:mm a Format(Ex: 04:40 PM)")
	private Date startTime;
	
	@NotBlank(message = "Please provide meeting end time in hh:mm a Format(Ex: 04:40 PM)")
	@NotNull(message = "Please provide meeting end time in hh:mm a Format(Ex: 04:40 PM)")
    private Date endTime;
	
    //private String modeofinterview;

}
