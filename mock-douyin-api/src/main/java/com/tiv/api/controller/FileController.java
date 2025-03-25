package com.tiv.api.controller;

import com.tiv.api.config.MinIOConfig;
import com.tiv.common.enums.FileTypeEnum;
import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.exception.GraceException;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.bo.UpdateUserBO;
import com.tiv.model.pojo.Users;
import com.tiv.service.UserService;
import com.tiv.service.utils.MinIOUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinIOConfig minIOConfig;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
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

    @PostMapping("/modifyImage")
    public GraceJSONResult modifyImage(@RequestParam String userId,
                                       @RequestParam Integer type,
                                       @RequestBody MultipartFile file) {
        if (type != FileTypeEnum.BG_IMG.type && type != FileTypeEnum.FACE.type) {
            throw new GraceException(ResponseStatusEnum.FILE_FORMAT_FAILED);
        }
        String filename = file.getOriginalFilename();
        try {
            MinIOUtil.uploadFile(minIOConfig.getBucketName(), filename, file.getInputStream());
        } catch (Exception e) {
            throw new GraceException(ResponseStatusEnum.FILE_UPLOAD_FAILED);
        }
        String imgUrl = String.format("%s/%s/%s", minIOConfig.getFileHost(), minIOConfig.getBucketName(), filename);
        UpdateUserBO updateUserBO = new UpdateUserBO();
        updateUserBO.setId(userId);
        if (type == FileTypeEnum.BG_IMG.type) {
            updateUserBO.setBgImg(imgUrl);
        } else {
            updateUserBO.setFace(imgUrl);
        }
        Users user = userService.updateUser(updateUserBO);
        return GraceJSONResult.ok(user);
    }
}
