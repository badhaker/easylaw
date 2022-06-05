package com.vedalegal.service;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vedalegal.entity.MeetingScheduleEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.repository.UserRepository;
import com.vedalegal.request.ScheduleMeeting;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;

@Service
public class ScheduleMeetService {

	
	@Autowired
	MeetingScheduleRepository meetingScheduleRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Value("${twilio.link}")
	private String twilio_link;
	
	@Autowired
	private EmailService emailService;
	
	public CommonSuccessResponse scheduleMeeting(ScheduleMeeting scheduleReq) throws AddressException, IOException, MessagingException {
		
		UserEntity client=userRepo.findById(scheduleReq.getClientId()).orElse(null);
		UserEntity lawyer=userRepo.findById(scheduleReq.getLawyerId()).orElse(null);

		MeetingScheduleEntity meetingInfo =MeetingScheduleEntity.builder()
		.meetingDate(scheduleReq.getMeetingDate())
		.startTime(scheduleReq.getStartTime())
		.endTime(scheduleReq.getEndTime())
		.lawyerId(lawyer)
		.clientId(client)
		.build();	

		meetingScheduleRepo.save(meetingInfo);
		Long millisecondsStartTime = scheduleReq.getStartTime().getTime() - AppConstant.TimeZone.IST_TIME_ZONE;
		Long millisecondsEndTime = scheduleReq.getEndTime().getTime() - AppConstant.TimeZone.IST_TIME_ZONE;

		
		String twilloRoomName = "twilio" + scheduleReq.getLawyerId() + scheduleReq.getClientId()
		 + new Date().getTime();
		String clientUrl = twilio_link + twilloRoomName + "?name=" + client.getName().split(" ")[0]
		+ "&start_time=" + millisecondsStartTime + "&end_time=" + millisecondsEndTime;
		String lawyerUrl = twilio_link + twilloRoomName + "?name=" + lawyer.getName().split(" ")[0]
				+ "&start_time=" + millisecondsStartTime + "&end_time=" + millisecondsEndTime;

		meetingInfo.setRoomName(twilloRoomName);
		meetingInfo.setTwilioClientLink(clientUrl );
		meetingInfo.setTwilioLawyerLink(lawyerUrl );
		meetingScheduleRepo.save(meetingInfo);

		emailService.sendEmail(client.getEmail(), "Easylaw- Meeting Scheduled.", 
				"Dear " +client.getName().split(" ")[0]+", \n \n \t Your meeting has been scheduled with Easylaw through video call on"
				+ " "+scheduleReq.getMeetingDate() +" at "+scheduleReq.getStartTime().getHours()+":"+scheduleReq.getStartTime().getMinutes() +"."
				+ "Please use the below link to start the video call.\n"
				+ " Kindly make sure that you have access to camera and internet for the video call on scheduled date and time. \n"
				+ " "+  clientUrl  +"\nThe above mentioned Video URL will become active 15 minutes before the scheduled time of "
				+ "interview and will not work after the interview is over.\n\n Thank you!");
		
		emailService.sendEmail(lawyer.getEmail(), "Easylaw- Meeting Scheduled.", 
				"Dear " +lawyer.getName().split(" ")[0]+", \n \n \t Your meeting has been scheduled with our client '"+client.getName().split(" ")[0] +"' through video call on"
				+ " "+scheduleReq.getMeetingDate() +" at "+scheduleReq.getStartTime().getHours()+":"+scheduleReq.getStartTime().getMinutes() +"."
				+ "Please use the below link to start the video call on scheduled date and time..\n"
				+ "Kindly make sure that you have access to camera and internet for the video call. \n"
				+ " "+  lawyerUrl  +"\nThe above mentioned Video URL will become active 15 minutes before the scheduled time of "
				+ "interview and will not work after the interview is over.\n\n Thank you!");
		
		
		return new CommonSuccessResponse(true);
	}

}
