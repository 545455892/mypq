package com.mypq.excel;

import com.alibaba.excel.EasyExcel;
import com.mypq.excel.model.MyColumn;

public class Test {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\UncleY\\Desktop\\11111.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, MyColumn.class, new MyColumnListener()).sheet().doRead();

    }

}
