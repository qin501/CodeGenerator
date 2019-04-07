package cn.wsq.entity;
/*
* 列对象
* */
public class Column {
    private String columnName;//列名称
    private String columnType;//列类型
    private String columnDbType;//列数据库类型
    private String columnComment;//列备注
    private String columnKey;//是否是主键
    private int decimal_digits;//小数位数
    private int colums_size;//字段长度

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

    public String getColumnDbType() {
        return columnDbType;
    }

    public void setColumnDbType(String columnDbType) {
        this.columnDbType = columnDbType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public int getDecimal_digits() {
        return decimal_digits;
    }

    public void setDecimal_digits(int decimal_digits) {
        this.decimal_digits = decimal_digits;
    }

    public int getColums_size() {
        return colums_size;
    }

    public void setColums_size(int colums_size) {
        this.colums_size = colums_size;
    }
}
