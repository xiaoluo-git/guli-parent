package com.guli.oos.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.guli.oos.service.OssService;
import com.guli.oos.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService{
    @Override
    public String upload(MultipartFile file) {
        OSSClient ossClient = null;
        String filePath = "";

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClient(ConstantPropertiesUtil.END_POINT, ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String prefix = UUID.randomUUID().toString().replaceAll("-", "");
            String path = prefix + originalFilename.substring(originalFilename.lastIndexOf("."));
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filePath = ConstantPropertiesUtil.FILE_HOST + "/" + datePath + "/" + path;
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME,filePath, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ossClient != null){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        // https://xlscw.oss-cn-beijing.aliyuncs.com/pic/6a12c10011324369a008a191947283df_2.png
        return "http://"+ ConstantPropertiesUtil.BUCKET_NAME + "." + ConstantPropertiesUtil.END_POINT + "/" + filePath;
    }
}
