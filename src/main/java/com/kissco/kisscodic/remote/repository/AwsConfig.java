//package com.kissco.kisscodic.remote.repository;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//public class AwsConfig {
//
//    private String accessKey ="";
//
//
//
//    private String secretKey="";;
//
//
//
//    private String region="";;
//
//
//    public AmazonS3 amazonS3() {
//        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//
//        return AmazonS3ClientBuilder.standard().withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .build();
//    }
//
//}
