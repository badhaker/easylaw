package com.vedalegal.entity;

	import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

	@Entity
	@Table(name="meeting_schedule")
	@Builder
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	
	public class MeetingScheduleEntity extends BaseEntity{
		
		@Column(name = "meeting_date", nullable = false, updatable = true)
		private String meetingDate ;

		@Column(name = "start_time", nullable = false, updatable = true)
		private Date startTime;

		@Column(name = "end_time", nullable = false, updatable = true)
		private Date endTime;
		
		@ManyToOne
		@JoinColumn(name="lawyer_id")
		private UserEntity lawyerId;
		
		@ManyToOne
		@JoinColumn(name="client_id")
		private UserEntity clientId;
		
		@Column(name="is_recording_available", columnDefinition = "enum('YES','NO')")
		@Enumerated(EnumType.STRING)
		private com.vedalegal.enums.IsRecordingAvailable isRecordingAvailable;
		
		@Column(name="is_meeting_cancelled",nullable=true,updatable = true)
		@ColumnDefault(value = "false")
	    private boolean isMeetingCancelled;
		
		@Column(name="room_ssid")
		private String roomSsid;
		
		@Column (name="composition_ssid")
		private String compositionSsid;
		
		@Column(name="composition_status")
		private String compositionStatus;
		
		@Column(name="meeting_status")
		private String meetingStatus; 
		
		@Column(name="room_name")
		private String roomName;
		
		@Column(name="twilio_client_link")
		private String twilioClientLink;
		
		@Column(name="twilio_lawyer_link")
		private String twilioLawyerLink;
		
		@Column(name="duration")
		private Long duration; 
		
	}


