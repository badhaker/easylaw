package com.vedalegal.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vedalegal.exception.AppException;
import com.vedalegal.resource.AppConstant;

@Service
public class FileUploadService {

	@Value("${app.upload.dir:${user.home}}")
	public String uploadDir;

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${AWS_BUCKET_NAME}")
	private String bucketName;

	public String uploadFile(MultipartFile file) {
		Path copyLocation = null;

		try {
			copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
			// throw new Exception("Could not store file " + file.getOriginalFilename()+".
			// Please try again!");
		}
		String fileLocation = uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename());
		return fileLocation;
	}

	@Async
	public void uploadFile(MultipartFile multipartFile, String filePath) throws IOException {
		InputStream streamToUpload = null;
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(multipartFile.getContentType());
		metaData.setContentLength(multipartFile.getSize());
		try {
			streamToUpload = multipartFile.getInputStream();
			amazonS3.putObject(new PutObjectRequest(bucketName, filePath, streamToUpload, metaData)
					.withCannedAcl(CannedAccessControlList.Private));
		} catch (SdkClientException e) {
			throw new AppException(AppConstant.ErrorTypes.UPLOAD_FILE_EXCEPTION,
					AppConstant.ErrorCodes.UPLOAD_FILE_EXCEPTION_CODE, e.getMessage());

		} finally {
			streamToUpload.close();
		}
	}

	public String generatePreSignedUrlForFileDownload(String fileKey) {

		if (fileKey == null || fileKey.isEmpty()) {
//			throw new AppException(AppConstant.ErrorTypes.PATH_NULL_FOR_FILE,
//					AppConstant.ErrorCodes.PATH_NULL_FOR_FILE_ERROR_CODE,
//					AppConstant.ErrorMessage.PATH_NULL_FOR_FILE_ERROR_MESSAGE);
			
			return "";
		}

		/*
		 * Date expirationDate = new Date(); long expTimeMillis =
		 * expirationDate.getTime();
		 *//** Expire time for 15 min. */
		/*
		 * 
		 * // expTimeMillis += 900000;
		 *//** Expire time for I hour *//*
										 * expTimeMillis += 1000 * 60 * 60; expirationDate.setTime(expTimeMillis);
										 */

		GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileKey)
				.withMethod(HttpMethod.GET);
		URL presignedUrl = amazonS3.generatePresignedUrl(presignedUrlRequest);
		System.out.println("S3 generated presigned url");
		String presignedUrlInString = presignedUrl.toString();
		return presignedUrlInString;

	}
	@Async
	public void deletefile(String keyName) {
		if (keyName==null || keyName =="") {
			return;
		}
		DeleteObjectRequest deleteRequest=new DeleteObjectRequest(bucketName,keyName);
		amazonS3.deleteObject(deleteRequest);
	}
}
