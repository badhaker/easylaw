package com.vedalegal.service;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.vedalegal.entity.MeetingScheduleEntity;
import com.vedalegal.exception.AppException;
import com.vedalegal.request.ScheduleMeeting;
import com.vedalegal.resource.AppConstant;

@Service
public class TwilioService {

	@Value("${ACCOUNT_SID}")
	private String ACCOUNT_SID;
	@Value("${AUTH_TOKEN}")
	private String AUTH_TOKEN;
	@Value("${COMPOSITION_URL}")
	private String COMPOSITION_URL;
	@Value("${StatusCallback}")
	private String StatusCallbackUrl;
	@Value("${MEDIA_URI}")
	private String MEDIA_URI;

	@Autowired
	MeetingScheduleRepository meetingScheduleRepository;

	@Autowired
	RestTemplate restTemplate;

	public boolean verify(String roomName) {
		MeetingScheduleEntity ScheduleEntity = meetingScheduleRepository.findByRoomName(roomName);

		if (ScheduleEntity == null) {
			throw new AppException(AppConstant.ErrorTypes.NULL_ENTITY, AppConstant.ErrorCodes.INVALID_INPUT_ERROR_CODE,
					AppConstant.ErrorMessage.NO_ROOM_FIND);
		}
//		 ScheduleMeeting interviewScheduleEntity = ScheduleEntity.getInterviewSchedule();
//		Date startDate = ScheduleEntity.getStartTime();

//		System.out.println(startDate);
//		 Date endDate=ScheduleEntity.getEndTime();
		// String startTime1=new Date(startTime).toString();
		// Date endTime1=new Date(endTime);
		// if(!(startDate.equals(startTime1)&& endDate.equals(endTime) ))
		/// throw new AppException(AppConstants.ErrorTypes.INVALID_INPUT_ERROR,
		/// AppConstants.ErrorCodes.INVALID_INPUT_ERROR_CODE,AppConstants.ErrorMessages.TIME_NOT_VALID);
		/*
		 * Date now=new Date(); Long currentTimeInMilliSeconds=now.getTime();
		 * if(!(startTime<=currentTimeInMilliSeconds &&
		 * endTime>=currentTimeInMilliSeconds)) throw new
		 * AppException(AppConstants.ErrorTypes.INVALID_INPUT_ERROR,
		 * AppConstants.ErrorCodes.INVALID_INPUT_ERROR_CODE,AppConstants.ErrorMessages.
		 * TIME_NOT_VALID);
		 */
		return true;
	}

	
	 public MeetingScheduleEntity roomDetails(String roomName) {
	 MeetingScheduleEntity scheduleEntity = meetingScheduleRepository.findByRoomName(roomName); 
	 return scheduleEntity;
	 
	 }
	 
	
	public void performComposition(HttpServletRequest requestObject,
			@RequestParam MultiValueMap<String, String> requestParams) {

		String RoomStatus = "";
		String StatusCallbackEvent = "";
		MeetingScheduleEntity meetingEntity = null;
		String RoomName = "";
		if (requestParams.containsKey("RoomStatus")) {
			RoomStatus = (String) requestParams.getFirst("RoomStatus");
		}
		if (requestParams.containsKey("StatusCallbackEvent")) {
			StatusCallbackEvent = (String) requestParams.getFirst("StatusCallbackEvent");
		}
		if (RoomStatus != null && RoomStatus != "" && RoomStatus.equalsIgnoreCase("Completed")
				&& !StatusCallbackEvent.equalsIgnoreCase("composition-available")) {
			String RoomSid = (String) requestParams.getFirst("RoomSid");
			RoomName = (String) requestParams.getFirst("RoomName");
			meetingEntity = meetingScheduleRepository.findByRoomName(RoomName);
			if (meetingEntity != null) {
				meetingEntity.setRoomSsid(RoomSid);
				meetingEntity.setMeetingStatus("Completed");
				meetingScheduleRepository.save(meetingEntity);
				ResponseEntity<String> urlResponse = null;
				HttpHeaders httpHeaders = new HttpHeaders();
				MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.add("RoomSid", RoomSid);
				map.add("StatusCallback", StatusCallbackUrl);
				map.add("Format", "mp4");
				map.add("AudioSources", "*");
				map.add("VideoLayout", "{\r\n" + "      \"grid\":{\r\n" + "        \"video_sources\":[\"*\"]\r\n"
						+ "      }\r\n" + "    }");
				// ResponseEntity<String> response = restTemplate.postForEntity( url, request ,
				// String.class );
				String user = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
				String authorizationHeader = "Basic "
						+ DatatypeConverter.printBase64Binary((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes());
				// restTemplate.getInterceptors().add(
				// new BasicAuthorizationInterceptor(ACCOUNT_SID, AUTH_TOKEN));
				httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				httpHeaders.add("User_Agent", user);
				httpHeaders.add("Authorization", authorizationHeader);
				restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
						httpHeaders);
				urlResponse = restTemplate.exchange(COMPOSITION_URL, HttpMethod.POST, request, String.class);
				JSONObject jsonObject = new JSONObject(urlResponse.getBody());
				// JSONObject data=jsonObject.getJSONObject("responseObject");
				// ObjectMapper mapper = new ObjectMapper();
				// JsonNode json = mapper.readTree(urlResponse);
				if (urlResponse.getStatusCode() == HttpStatus.CREATED) {
					System.out.println("response received");
					System.out.println(urlResponse.getBody());
					String CompositionSid = (String) jsonObject.get("sid");
					String Composition_status = (String) jsonObject.get("status");
					meetingEntity.setCompositionStatus(Composition_status);
					meetingEntity.setCompositionSsid(CompositionSid);
					meetingScheduleRepository.save(meetingEntity);
				}
			}
		} else if (StatusCallbackEvent.equalsIgnoreCase("composition-available")) {
			if (requestParams.containsKey("CompositionSid")) {
				String CompositionSid = (String) requestParams.getFirst("CompositionSid");
				meetingEntity = meetingScheduleRepository.findByCompositionSsid(CompositionSid);
				if (meetingEntity != null) {
					if (requestParams.containsKey("Duration")) {
						String duration = (String) requestParams.getFirst("Duration");
						if (!duration.isEmpty() && duration != null) {
							Long durationInMillisecods = Long.parseLong(duration) * 1000;
							meetingEntity.setDuration(durationInMillisecods);
						}
					}
					meetingEntity.setCompositionStatus("done");
					meetingScheduleRepository.save(meetingEntity);
				}
			}
		} else {
			throw new AppException(AppConstant.ErrorTypes.NULL_ENTITY, AppConstant.ErrorCodes.INVALID_INPUT_ERROR_CODE,
					AppConstant.ErrorMessage.COMPOSITION_NOT_AVIALABLE);

		}
	}

	public String generateDownloadUri(Long meetingId) {
		String CompositionSid = "";
		String compositionStatus = "";
		HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
		RestTemplate restTemplate1 = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
		MeetingScheduleEntity videoEntity = meetingScheduleRepository.findById(meetingId).orElse(null);
		if (videoEntity != null) {
			CompositionSid = videoEntity.getCompositionSsid();
			compositionStatus = videoEntity.getCompositionStatus();
		}
		if (!compositionStatus.equalsIgnoreCase("done")) {
			throw new AppException(AppConstant.ErrorTypes.NULL_ENTITY, AppConstant.ErrorCodes.INVALID_INPUT_ERROR_CODE,
					AppConstant.ErrorMessage.COMPOSITION_NOT_AVIALABLE);
		}
		ResponseEntity<String> urlResponse = null;
		HttpHeaders httpHeaders = new HttpHeaders();

		String user = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
		String authorizationHeader = "Basic "
				+ DatatypeConverter.printBase64Binary((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes());

		httpHeaders.add("User_Agent", "curl/7.55. 1");
		httpHeaders.add("Authorization", authorizationHeader);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		System.out.println("before exchange");
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		urlResponse = restTemplate1.exchange(MEDIA_URI + CompositionSid + "/Media?Ttl=3600", HttpMethod.GET, request,
				String.class);

		JSONObject JSONObject = new JSONObject(urlResponse.getBody());
		String reponse = (String) JSONObject.get("redirect_to");
		if (!(urlResponse.getStatusCode() == HttpStatus.FOUND)) {
			throw new AppException(AppConstant.ErrorTypes.NULL_ENTITY, AppConstant.ErrorCodes.INVALID_INPUT_ERROR_CODE,
					AppConstant.ErrorMessage.RESPONSE_NOT_RECIEVED);
		}
		return reponse;
	}
}
