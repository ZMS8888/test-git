package com.zm.controller;



import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private FastFileStorageClient fileStorageClient;

    @GetMapping("/get")
    public String test() {
        System.out.println(55);
        String fileUrl = "user.xlsx";
        byte[] bytes = fileUrl.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        StorePath storePath = fileStorageClient.uploadFile(stream, bytes.length, "xlsx", null);
        System.out.println(storePath);
        System.out.println(storePath.getPath());
        return storePath.getPath();
    }


    @GetMapping("/down")
    public void down(HttpServletResponse response) throws IOException {
        String group = "group1";
        String fileName = "user";
        String fileUrl = "M00/00/0D/wKhnlGNfjRSAL9EnAAAACcEi1Mk37.xlsx";
        DownloadByteArray byteArray = new DownloadByteArray();
        byte[] bytes = fileStorageClient.downloadFile(group, fileUrl, byteArray);
        ByteArrayInputStream resource = new ByteArrayInputStream(bytes);
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=\"" + fileName+ "\"; filename*=utf-8'zh_cn'" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        BufferedInputStream bis = new BufferedInputStream(resource);
        XSSFWorkbook wb = new XSSFWorkbook(bis);
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        wb.write(os);
        os.flush();
        os.close();

    }
}
