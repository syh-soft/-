package com.haley.demo.controller;


import cn.jpush.api.push.PushResult;
import com.haley.demo.entity.ApkEntity;
import com.haley.demo.entity.UserEntity;
import com.haley.demo.service.UserService;
import com.haley.demo.utils.ApkInfoUtil;
import com.haley.demo.utils.SendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/file")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "" +
                "" +
                "E:/uploadPath/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);

            ApkEntity maps = ApkInfoUtil.readAPK(dest);
            userService.saveAPK(maps);

            LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }

    // 获取接口



    // 下载文件




}
