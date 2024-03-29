package com.entiy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class FillData {


    private String name;
    private double number;
    private Date date;
    private Integer age;
}
