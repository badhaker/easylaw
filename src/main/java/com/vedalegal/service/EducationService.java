package com.vedalegal.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.LawyerEducationEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.modal.LawyerEducation;
@Service
public class EducationService {

	public LawyerEducationEntity convertToEntity(LawyerEducation edu, UserEntity lawyer) {
		
//		Date startdateYear =edu.getStartDate();
//		SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
//		String date1=formatter1.format(startdateYear);
//		
//		LocalDate ld = LocalDate.parse(date1);
//		System.out.println("Year "+ld);
		
		
		
		
		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////		format.parse(edu.getStartDate());
//		int str = edu.getStartDate().getYear();
//		Date dateFin = null;
//		try {
//			dateFin = (Date) format.parse(String.valueOf(str));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(edu.getStartDate());
//		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    		
//	    Date date = Calendar.getInstance().getTime();
//	   String ss = formatter.format(edu.getStartDate());
//	   LocalDate startYear = LocalDate.parse(ss, formatter);
//	   
//	   String ss1 = formatter.format(edu.getEndDate());
//	   LocalDate endYear = LocalDate.parse(ss1, formatter);
	   
	   
//	   System.out.println(edu.getStartDate().getYear());
		return LawyerEducationEntity.builder()
				.institution(edu.getInstitution())
				.course(edu.getCourse())
				.lawyerId(lawyer)
				.startDate(edu.getStartDate())
				.endDate(edu.getEndDate())
				.build();
	}

}
