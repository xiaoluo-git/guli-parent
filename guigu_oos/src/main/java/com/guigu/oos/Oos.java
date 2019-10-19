package com.guigu.oos;


import com.aliyun.oss.OSSClient;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Oos {

    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIhiyF1bmbVDok";
        String accessKeySecret = "y2v0iHgOet2NcQVyqpkNQ4J8VYeCht";

// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

// 上传字符串。
        ossClient.createBucket("myguli-2048");



// 关闭OSSClient。
        ossClient.shutdown();
    }
}
