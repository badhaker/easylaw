package com.vedalegal.request;

import com.vedalegal.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawyerUserRemark {

    private OrderStatus orderStatus;
    private String review;

}
