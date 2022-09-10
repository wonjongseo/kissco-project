package com.kissco.kisscodic.remote.repository.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface RemoteUpload {
     String upload(MultipartFile multipartFile) throws IOException;
     String upload(File multipartFile) throws IOException;
}
