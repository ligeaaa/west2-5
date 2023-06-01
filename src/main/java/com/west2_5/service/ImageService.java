package com.west2_5.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String upload(MultipartFile multipartFile);
}
