package com.haley.demo.utils;

import com.haley.demo.entity.ApkEntity;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApkInfoUtil {
    public static ApkEntity readAPK(File apkUrl) {
        ApkEntity entity = new ApkEntity();
        Map<String, Object> resMap = new HashMap<String, Object>();
        try (ApkFile apkFile = new ApkFile(apkUrl)) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            entity.setFilename(apkMeta.getName());
            entity.setPkgname(apkMeta.getPackageName());
            entity.setVersioncode(apkMeta.getVersionCode()+"");
            entity.setVersionname(apkMeta.getVersionName()+"");

            for (UseFeature feature : apkMeta.getUsesFeatures()) {
                System.out.println(feature.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;

    }
}
