package com.atguigu.manager.controller;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/file")
@Controller
public class FileUploadController {

    @Value("${IMAGE_SERVER_URL}")
    String imageServerUrl;

    @Autowired
    FastFileStorageClient storageClient;

    // POST http://localhost:8180/restful/file/upload

    @RequestMapping(value = "/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            String filename = file.getOriginalFilename();
            String extName = StringUtils.substringAfterLast(filename, ".");
            StorePath storePath = storageClient.uploadFile(file.getBytes(), extName);

            String url = imageServerUrl + storePath.getFullPath();

            map.put("state", "SUCCESS");
            map.put("url", url);
            map.put("size", file.getSize());
            map.put("original", filename);
            map.put("type", file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
