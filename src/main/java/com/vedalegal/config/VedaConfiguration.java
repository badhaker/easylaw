package com.vedalegal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class VedaConfiguration {
	
	@Value("${AWS_ACCESS_KEY_ID}")
	private String accessKeyId;
	
	@Value("${AWS_SECRET_ACCESS_KEY}")
	private String secretAccessKey;
	
	@Value("${AWS_REGIONS}")
	private String awsRegions;

	 @Bean(name = "amazonS3")
		public AmazonS3 getS3()
		{
		 System.out.println(accessKeyId+"'''''''"+secretAccessKey);
	 		 AmazonS3 amazonS3 =AmazonS3ClientBuilder.standard().withCredentials
	 				(new AWSStaticCredentialsProvider
							(new BasicAWSCredentials(accessKeyId, secretAccessKey))
							).withRegion(awsRegions).build();
	 		 return amazonS3;
		}
	 @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
}
