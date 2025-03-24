package com.tiv.api.controller;

import com.tiv.api.config.MinIOConfig;
import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.exception.GraceException;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.service.utils.MinIOUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinIOConfig minIOConfig;

    @PostMapping("upload")
    public GraceJSONResult upload(@RequestBody MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            MinIOUtil.uploadFile(minIOConfig.getBucketName(), filename, file.getInputStream());
        } catch (Exception e) {
            throw new GraceException(ResponseStatusEnum.FILE_UPLOAD_FAILED);
        }
        String url = String.format("%s/%s/%s", minIOConfig.getFileHost(), minIOConfig.getBucketName(), filename);
        return GraceJSONResult.ok(url);
    }
}
