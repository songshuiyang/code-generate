package com.songsy.v2;

/**
 * 数据库表字段属性
 *
 * @author songsy
 * @date 2019/11/8 13:34
 */
public class ColumnProperty {
    /**
     * 数据库字段名称
     */
    private String columnName;
    /**
     * 数据库字段类型
     */
    private String columnType;
    /**
     * 数据库字段对应的Java类型
     */
    private String javaType;
    /**
     * 数据库字段首字母小写且去掉下划线字符串
     */
    private String changeColumnName;
    /**
     * 数据库字段注释
     */
    private String columnComment;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getChangeColumnName() {
        return changeColumnName;
    }

    public void setChangeColumnName(String changeColumnName) {
        this.changeColumnName = changeColumnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    @Override
    public String toString() {
        return "ColumnProperty{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", javaType='" + javaType + '\'' +
                ", changeColumnName='" + changeColumnName + '\'' +
                ", columnComment='" + columnComment + '\'' +
                '}';
    }
}
