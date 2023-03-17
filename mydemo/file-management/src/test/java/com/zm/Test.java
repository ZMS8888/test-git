package com.zm;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;

public class Test {

    @Autowired
    private FastFileStorageClient fileStorageClient;


    @org.junit.Test
    public void test() {
        System.out.println(55);
        String fileUrl = "user.xlsx";
        byte[] bytes = fileUrl.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        StorePath storePath = fileStorageClient.uploadFile(stream, bytes.length, "xlsx", null);
        System.out.println(storePath);
        System.out.println(storePath.getPath());

    }
}
