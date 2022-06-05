
package com.vedalegal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.entity.MeetingScheduleEntity;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
//import com.vedalegal.service.CallSenderService;
import com.vedalegal.service.TwilioService;

@RestController
public class TwilioController {
	@Autowired
	private TwilioService twilioService;


//	CallSenderService callSenderService;

	@GetMapping("/verify/room")
	public ResponseEntity<BaseApiResponse> verifyRoom(@RequestParam String roomName) {
		boolean ab = twilioService.verify(roomName);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(ab);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	
	 @GetMapping("/roomDetails") public ResponseEntity<BaseApiResponse>
	 detailsRoom(@RequestParam String roomName) { MeetingScheduleEntity
	 videoInterviewScheduleEntity = twilioService.roomDetails(roomName);
	 BaseApiResponse baseApiResponse =
	 ResponseBuilder.getSuccessResponse(videoInterviewScheduleEntity); 
	 return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK); }
	 
	 
	@PostMapping("/callbackUriTwilio")
	public ResponseEntity<BaseApiResponse> callbackFunction(HttpServletRequest request,@RequestParam MultiValueMap<String, String> requestParams) {
		twilioService.performComposition(request, requestParams);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse("Response");
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/downloadRecordingUri")
	public ResponseEntity<BaseApiResponse> generateDownloadUri(@RequestParam Long meetingId) {
		String reponse = twilioService.generateDownloadUri(meetingId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(reponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

//	@PostMapping("/voicecall")
//	public ResponseEntity<BaseApiResponse> sendCall(@Valid @RequestBody CallRequest callRequest)
//			throws URISyntaxException {
//		callSenderService.sendCall(callRequest);
//		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse("Response");
//		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
//	}
	
}
