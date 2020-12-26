package com.mypq.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MyColumn {

    // id
    @ExcelProperty(index = 0,value = "数据库")
    private String scheme;
    // 名称
    @ExcelProperty(index = 1,value = "数据表")
    private String tableName;
    // 创建时间
    @ExcelProperty(index = 2,value = "表明")
    private String tableDesc;
    // 描述
    @ExcelProperty(index = 3,value = "字段名")
    private String columnName;

    // 描述
    @ExcelProperty(index = 4,value = "字段类型")
    private String columnType;

    // 描述
    @ExcelProperty(index = 5,value = "字段含义")
    private String columnDesc;

    // 描述
    @ExcelProperty(index = 6,value = "长度")
    private String columnLength;

    // 描述
    @ExcelProperty(index = 7,value = "精度")
    private String columnPrecision;

    // 描述
    @ExcelProperty(index = 8,value = "主键")
    private String primaryKeyAble;

    // 描述
    @ExcelProperty(index = 9,value = "能否为NULL（0/1）")
    private String nullable;

}
