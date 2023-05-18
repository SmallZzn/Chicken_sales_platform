package com.zhao.salechicken.controller;

import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import com.zhao.salechicken.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件的上传和下载
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${salechicken.path}")
    private String basePath;

     /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file 是一个临时文件，需要转存到指定位置，否则请求完成后临时文件会删除
        log.info("file:{}", file.toString());

        /**
         * 原始文件名
         * 不推荐，文件名可能重复，会导致覆盖掉原先图片
         */
        String originalFileName = file.getOriginalFilename();
        //截取文件后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        /**
         * 使用uuid随机生成文件名，防止重名
         */
        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //目录不存在
            dir.mkdirs();
        }

        try {
            //将了临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }


    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param response 用来获取输出流
     */
//    @GetMapping("/download")
//    public void download(String fileName, HttpServletResponse response) {
//        try {
//            //输入流，通过输入流读取文件内容
//            System.out.println(basePath + fileName);
//            FileInputStream fileInputStream = new FileInputStream(new File(basePath + fileName));
//            //输出流，通过输出流将文件写回浏览器，在浏览器中展示图片
//            ServletOutputStream outputStream = response.getOutputStream();
//
//            int len = 0;
//            byte[] bytes = new byte[1024];
//            while ((len = fileInputStream.read(bytes)) != -1) {
//                outputStream.write(bytes, 0, len);
//                outputStream.flush();
//            }
//            outputStream.close();
//            fileInputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        log.info("filename:" + fileName);
        String[] split = fileName.split("/");
        String name = split[split.length - 1];

        File file = new File(basePath + fileName);
        if (file.exists()) {
            log.info("下载图片文件名：" + basePath + fileName);
            ServletOutputStream outputStream = response.getOutputStream();

            //设置输出流格式
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));

            //任意类型的二进制流数据
            response.setContentType("application/octet-stream");
            //读取文件字节流
            outputStream.write(FileUtil.readBytes(file));
            outputStream.flush();
            outputStream.close();
        }
    }
}
