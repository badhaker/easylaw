package com.vedalegal.response;

import java.util.List;

import com.vedalegal.modal.LawyerDetailResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqAndReview {

	private LawyerDetailResponse lawyerDetail;
	List<LawyerFAQGetList> faqList;
	List<ClientFeedbackGet> clientFeedbackList;
}
