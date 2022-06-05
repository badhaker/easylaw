package com.vedalegal.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.MeetingScheduleEntity;

public interface MeetingScheduleRepository extends JpaRepository<MeetingScheduleEntity, Long> {

	MeetingScheduleEntity findByRoomName(String roomName);

	MeetingScheduleEntity findByCompositionSsid(String compositionSid);

}
