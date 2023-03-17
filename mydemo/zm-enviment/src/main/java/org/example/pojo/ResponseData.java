package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@Slf4j
public class ResponseData implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SUCCESS_CODE = "200";
    public static final String IOT_SUCCESS_CODE = "0";
    public static final String FAIL_CODE = "300";

    private String code;

    private Object data;

    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateTime = new Date();

    public static ResponseData success(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setData(data);
        responseData.setCode(SUCCESS_CODE);
        responseData.setMessage("操作成功");
        return responseData;
    }

}
