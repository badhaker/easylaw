package com.vedalegal.modal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceImageDetails {

    private Long id;
    private String type;
    private String imageURI;
    private String imgDescription;
}
