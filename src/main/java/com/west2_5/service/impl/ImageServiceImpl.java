package com.west2_5.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import com.west2_5.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Date: 2023/6/1
 * @Author: RuiLin
 * @Description: 图床相关操作
 */

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    public String upload(MultipartFile multipartFile) {

        String api = "https://smms.app/api/v2/upload";
        String auth = "FlO1AGb4lMrGfZyZ2YAOdfcaVgo1ltA2";
        // 模拟游览器请求，否则出现 403 Forbidden
        String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

        String fileName = multipartFile.getOriginalFilename();
        String suffix=".";
        File tempFile = null;
        try {
            //获取图片后缀（png,jpg等）
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                 suffix = fileName.substring(dotIndex + 1);
            }
            //临时文件名生成规则：prefix(必须不少于三个字符) + 随机字符串 + suffix
            tempFile = File.createTempFile("pixel_", suffix);
            multipartFile.transferTo(tempFile);
        } catch (IOException e) {
            //
        }

        // FIXME: 图床无法上传重复图片
        HashMap<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("smfile", tempFile);

        JSONObject body = JSONObject.parseObject(
                HttpRequest.post(api)
                .header("Authorization", auth)
                .header("User-Agent", agent)
                .form(paramMap)
                .timeout(20000)
                .execute().body());

        JSONObject data = body.getJSONObject("data");

        return data.getString("url");
    }

}
