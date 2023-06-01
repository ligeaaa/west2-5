package com.west2_5.controller;

import com.west2_5.common.ResponseResult;
import com.west2_5.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;


    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam MultipartFile file) {
        String url = imageService.upload(file);
        return ResponseResult.success(url);
    }


}
