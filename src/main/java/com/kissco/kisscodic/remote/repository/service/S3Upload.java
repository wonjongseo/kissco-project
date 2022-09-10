//package com.kissco.kisscodic.remote.repository.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.io.IOUtils;
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.util.UUID;
//
//
//@RequiredArgsConstructor
//@Service
//public class S3Upload implements RemoteUpload{
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private final AmazonS3 amazonS3;
//
//    @Override
//    public String upload(MultipartFile multipartFile) throws IOException {
//
//        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
//
//        System.out.println("fileName = " + fileName);
//
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(multipartFile.getInputStream().available());
//
//
//        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);
//
//        return amazonS3.getUrl(bucket, fileName).toString();
//
//    }
//    public String upload(File file) throws IOException {
//        System.out.println("file.getName() = " + file.getName());
//        MultipartFile multipartFile = new MockMultipartFile(file.getName(), new FileInputStream(file));
//        System.out.println("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());
//
//        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
//
//        System.out.println("fileName = " + fileName);
//
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(multipartFile.getInputStream().available());
//
//
//        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);
//
//        return amazonS3.getUrl(bucket, fileName).toString();
//    }
//
//}
