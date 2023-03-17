package com.zm.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonToObjectDTO {

    private Integer code;

    private String name;

    private String rule;


    private String value;

    private String example;

    private String autoIncrementConfigureType;

    private String uuid;

}
