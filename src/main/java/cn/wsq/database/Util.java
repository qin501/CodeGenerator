package cn.wsq.database;

import cn.wsq.entity.Column;
import cn.wsq.entity.Table;
import cn.wsq.xml.XmlUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Util {
    private String dbType;//数据库类型

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    private String driverName;
    private String userName;
    private String password;
    private String url;
    private String dbName;
    private String ip;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    /*
    *获取所有的数据库名称
    * */
    public List<String> getSchemas() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
       // Connection connection = DriverManager.getConnection(url, "root", "admin");
        Connection connection = DriverManager.getConnection(url, userName, password);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getCatalogs();
        List<String> list=new ArrayList<>();
        while(resultSet.next()){
            list.add(resultSet.getString(1));
        }
        resultSet.close();
        connection.close();
        return list;
    }
    /*
    * 获取表结构
    * */
    public List<Table> getDbInfo() throws ClassNotFoundException, SQLException {
        //加载转换器
        Map<String,String> convertMap= XmlUtil.readNu("typeConverter.xml");
        Class.forName(driverName);
        Properties props=new Properties();
        props.put("remarkReporting","true");
        props.put("user",userName);
        props.put("password",password);
        if(dbName!=null){
            url=url.replace("[db]",dbName);
        }
        if(ip!=null&&!ip.equals("")){
            url=url.replace("[ip]",ip);
        }else{
            url=url.replace("[ip]","127.0.0.1");
        }
        Connection connection = DriverManager.getConnection(url, userName, password);
        DatabaseMetaData metaData = connection.getMetaData();
        String schema=null;
        String catalog=null;
        if(dbType!=null&&dbType.toUpperCase().indexOf("ORACLE")>=0){
            schema=userName.toUpperCase();
            catalog=connection.getCatalog();
        }else{
            schema=userName;
            catalog=connection.getCatalog();
        }
        ResultSet tablers = metaData.getTables(catalog, schema, null, new String[] { "TABLE" });
        List<Table> list=new ArrayList<Table>();
        while(tablers.next()){
            Table table=new Table();
            String tableName=tablers.getString("TABLE_NAME");
            //如果为垃圾表
            if(tableName.indexOf("=")>=0||tableName.indexOf("$")>=0){
                continue;
            }
            //判断表名为全大写，则转换为小写
            if(tableName.toUpperCase().equals(tableName)){
                table.setName(tableName.toLowerCase());
            }else{
                table.setName(tableName);
            }
            //设置注释
            table.setComment(tablers.getString("REMARKS"));
            //获得主键
            ResultSet primaryKeys=metaData.getPrimaryKeys(catalog,schema,tableName);
            List<String> keys=new ArrayList<String>();
            while(primaryKeys.next()){
                String keyname=primaryKeys.getString("COLUMN_NAME");
                if(keyname.toUpperCase().equals(keyname)){
                    keyname=keyname.toLowerCase();
                }
                keys.add(keyname);
            }
            System.out.println("信息："+catalog+"\t"+schema+"\t"+tableName);
            //获得所有列
            ResultSet columnrs=metaData.getColumns(catalog,schema,tableName,null);
            List<Column> columnList=new ArrayList<Column>();
            while(columnrs.next()){
                Column column=new Column();
                String columnName=columnrs.getString("COLUMN_NAME");
                if(columnName.toUpperCase().equals(columnName)){
                    columnName=columnName.toLowerCase();
                }
                column.setColumnName(columnName);
                String columnDbType=columnrs.getString("TYPE_NAME");
                column.setColumnDbType(columnDbType);//数据库原始类型
                String typeName=convertMap.get(columnDbType);//获取转换后的类型
                if(typeName==null){
                    typeName=columnrs.getString("TYPE_NAME");
                }
                column.setColumnType(typeName);
                String remarks=columnrs.getString("REMARKS");
                if(remarks==null){
                    remarks=columnName;
                }
                column.setColumnComment(remarks);
                if(keys.contains(columnName)){//如果该列是主键
                    column.setColumnKey("PRI");
                    table.setKey(column.getColumnName());
                    table.setKeyType(column.getColumnType());
                }else{
                    column.setColumnKey("");
                }
                int decimal_digits=columnrs.getInt("DECIMAL_DIGITS");
                if(decimal_digits>0){
                    column.setColumnType("Double");//如果是小数设置为double
                }
                column.setDecimal_digits(decimal_digits);
                columnList.add(column);
            }
            columnrs.close();
            table.setColumns(columnList);
            list.add(table);
        }
        tablers.close();
        connection.close();
        return list;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Util util = new Util();
        util.setUserName("root");
        util.setPassword("admin");
        util.setUrl("jdbc:mysql://localhost:3306/security?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        util.setDriverName("com.mysql.cj.jdbc.Driver");
//        List<String> schemas = util.getSchemas();
        List<Table> dbInfo = util.getDbInfo();
        System.out.println(dbInfo);
    }
}
