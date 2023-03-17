package com.zm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSON;
import com.zm.entity.DTO.ImportDTO;
import com.zm.util.excel.ExcelUtils;
import org.apache.poi.openxml4j.opc.internal.ContentType;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExcelImportOrExport {

    /**
     * 读取Excel转换成json
     * @throws Exception
     */
    @Test
    public void import1() throws Exception {
        String url = "user.xlsx";
        File file = new File(url);
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println(file.getName());
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),file.getName(),null ,fileInputStream);
        JSONArray jsonArray = ExcelUtils.readMultipartFile(multipartFile);
        String parseObject = JSON.parseObject(String.valueOf(jsonArray), String.class);
        System.out.println(jsonArray);
        System.out.println(parseObject);
    }

    /**
     * 读取Excel，根据指定对象解析
     * @throws Exception
     */
    @Test
    public void import2() throws Exception {
        String url = "user.xlsx";
        File file = new File(url);
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println(file.getName());
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),file.getName(),null ,fileInputStream);
        List<ImportDTO> importDTOS = ExcelUtils.readMultipartFile(multipartFile, ImportDTO.class);
        for (ImportDTO importDTO : importDTOS) {
            System.out.println(importDTO);
        }
    }

    /**
     * 读取Excel，解析多个sheet
     * @throws Exception
     */
    @Test
    public void import3() throws Exception {
        String url = "user.xlsx";
        File file = new File(url);
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println(file.getName());
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),file.getName(),null ,fileInputStream);
        Map<String, JSONArray> stringJSONArrayMap = ExcelUtils.readFileManySheet(multipartFile);
        stringJSONArrayMap.forEach((key,value)->{
            System.out.println(key);
            System.out.println(value);
               }
        );
    }

    /**
     * 读取Excel，解析多个sheet
     * @throws Exception
     */
    @Test
    public void export() throws Exception {
        // 表头数据
        List<Object> head = Arrays.asList("姓名","年龄","性别","头像");
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("诸葛亮");
        user1.add(60);
        user1.add("男");
        user1.add("2");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(28);
        user2.add("女");
        user2.add("2");
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);
        // 导出数据
        ExcelUtils.exportFile("D:\\yl\\demo\\mydemo\\common-service","用户表", sheetDataList);
    }
}
