package cn.wsq;

import cn.wsq.database.Util;
import cn.wsq.entity.Table;
import cn.wsq.xml.ConfigXml;
import cn.wsq.xml.DatabaseXml;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UtilsTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
       /* Util util = new Util();
        Map<String, Map<String, String>> map = ConfigXml.readConfig();
        Map<String, String> mysqlMap = map.get("MYSQL");

        util.setDriverName(mysqlMap.get("driverName"));
//        util.setUrl(mysqlMap.get("url"));
        util.setDbType(mysqlMap.get("databaseTYPE"));
        util.setPassword("admin");
        util.setUserName("root");
        util.setUrl("jdbc:mysql://localhost:3306/security?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
//        util.setDriverName("com.mysql.cj.jdbc.Driver");
        List<Table> dbInfo = util.getDbInfo();


       String basePath=new File("").getAbsolutePath()+File.separator+"db";
//        if(!new File(basePath).exists()){
//            new File(basePath).mkdirs();
//        }
       // DatabaseXml.writeDatabaseXml(util,mysqlMap,basePath);

        List<Table> tableList = DatabaseXml.readDatabaseXml(basePath);
        System.out.println("完成了");*/
       String str="abcdefa";
        System.out.println(str.replace("a","j"));


    }
}
