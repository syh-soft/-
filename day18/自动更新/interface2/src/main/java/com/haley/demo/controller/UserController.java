package com.haley.demo.controller;

import cn.jpush.api.push.PushResult;
import com.haley.demo.entity.ApkEntity;
import com.haley.demo.entity.UserEntity;
import com.haley.demo.service.UserService;
import com.haley.demo.utils.Result;
import com.haley.demo.utils.SendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @program: demo
 * @description: 用户控制层
 * @author: haley
 * @create: 2021-07-09 19:14
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param id
     * @return 用户信息
     */
    @GetMapping("/queryById")
    public UserEntity queryById(int id){
        PushResult result = SendUtils.testSendPush();
        return userService.queryById(id);
    }

    @GetMapping("/queryByPhone")
    public UserEntity queryByPhone(String phone){
        return userService.queryByPhone(phone);
    }

    @PostMapping("/saveOne")
    public Result saveone(@RequestBody UserEntity userEntity){
        userService.save(userEntity);
        return Result.ok("保存成功");
    }

    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "nday17.apk";// 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = "E:/uploadPath/";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/octet-stream");//
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println(bis.toString());
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return response.getHeader("data");
    }

    @GetMapping("/queryAPK")
    public ApkEntity queryAPK(){
        return userService.queryAPK();
    }

    @PostMapping("/sendMessage")
    public Result sendMessage(@RequestBody UserEntity userEntity){
        PushResult result = SendUtils.testSendPush();
        return Result.ok("保存成功");
    }
   

}
