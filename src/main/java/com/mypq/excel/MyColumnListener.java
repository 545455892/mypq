package com.mypq.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.mypq.excel.model.MyColumn;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class MyColumnListener extends AnalysisEventListener<MyColumn> {

    private static String firstLine = "CREATE TABLE IF NOT EXISTS `%s`(\n";
    // 字段名 columnType nullable
    private static String columnLine = "`%s` %s %s,\n";
    private static String pkLine = "PRIMARY KEY (%s)";
    private static String lastLine = ") ENGINE=InnoDB DEFAULT CHARSET=utf8";


    // 用于存储读取到的数据
    private final List<MyColumn> myColumns = new ArrayList<MyColumn>();


    /**
     * 解析每条数据处理的动作
     * @param myColumn
     * @param analysisContext
     */
    @Override
    public void invoke(MyColumn myColumn, AnalysisContext analysisContext) {
        myColumns.add(myColumn);
        // do something
    }

    /**
     * 解析所有数据之后处理的动作
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // <表明，字段集合>
        Map<String, List<MyColumn>> myColumnMap = new HashMap<>();
        for (MyColumn myColumn : myColumns) {
            String tableName = myColumn.getTableName();
            List<MyColumn> myColumns = myColumnMap.get(tableName);
            if (CollectionUtils.isEmpty(myColumns)) {
                myColumns = new LinkedList<>();
            }
            myColumns.add(myColumn);
            myColumnMap.put(tableName, myColumns);
        }

        // <表明，SQL>
        Map<String, String> sqlMap = new HashMap<>();

        for (Map.Entry<String, List<MyColumn>> myColumnEntry : myColumnMap.entrySet()) {
            StringBuilder sql = new StringBuilder();
            List<String> pkList = new LinkedList<>();
            String tableName = myColumnEntry.getKey();
            sql.append(String.format(firstLine, tableName));

            List<MyColumn> myColumns = myColumnEntry.getValue();
            for (MyColumn myColumn : myColumns) {
                String columnName = myColumn.getColumnName();

                String columnType = myColumn.getColumnType();
                String columnLength = myColumn.getColumnLength();
                String columnPrecision = myColumn.getColumnPrecision();

                String value = null;
                switch (columnType){
                    case "int":
                        value = String.format("int(%s)", columnLength);
                        break;
                    case "float":
                        value = String.format("float(%s, %s)", columnLength, columnPrecision);
                        break;
                    case "varchar":
                        value = String.format("varchar(%s)", columnLength);
                        break;
                    default:
                        value = columnType;
                }

                String nullable = myColumn.getNullable();
                if ("1".equals(nullable)) {
                    nullable = "NULL";
                } else {
                    nullable = "NOT NULL";
                }

                String primaryKeyAble = myColumn.getPrimaryKeyAble();
                if ("1".equals(primaryKeyAble)) {
                    pkList.add(columnName);
                }

                sql.append(String.format(columnLine, columnName, value, nullable));
            }

            StringBuilder pkStr = new StringBuilder();
            for (String pk : pkList) {
                pkStr.append("`").append(pk).append("`,");
            }
            pkStr.deleteCharAt(pkStr.length()-1);

            sql.append(String.format(pkLine, pkStr.toString()));

            sql.append(lastLine);

            sqlMap.put(tableName, sql.toString());
        }

        for (Map.Entry<String, String> sqlEntry : sqlMap.entrySet()) {
            String tableName = sqlEntry.getKey();
            MyUI.appendSqlTextArea(String.format("--%s start------------------------------------------", tableName));
            MyUI.appendSqlTextArea(sqlEntry.getValue());
            MyUI.appendSqlTextArea(String.format("--%s end  ------------------------------------------", tableName));
        }

        // do something
        // 清理
        //datas.clear();
    }
}
