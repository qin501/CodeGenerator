package cn.wsq.xml;

import cn.wsq.database.Util;
import cn.wsq.entity.Column;
import cn.wsq.entity.Table;
import cn.wsq.util.Unicode;
import javafx.scene.control.Tab;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseXml {
    /*
    * 把util里的信息，写入outpath路径中
    * */
    public static void writeDatabaseXml(Util util, Map<String,String> propertyMap,String outPath) throws SQLException, ClassNotFoundException {
        Document doc= DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element root = doc.addElement("db");
        root.addAttribute("name",util.getDbName());
        root.addAttribute("driverName",util.getDriverName());
        root.addAttribute("userName",util.getUserName());
        root.addAttribute("password",util.getPassword());
        root.addAttribute("url",util.getUrl());
        for(String key:propertyMap.keySet()){
            Element element = root.addElement("property");
            element.addAttribute("name",key);
            element.setText(propertyMap.get(key));
        }
        //获取表结构
        List<Table> tableList=util.getDbInfo();
        for(Table table:tableList){
            int keycount=0;//主键数量
            for(Column column:table.getColumns()){
                if(column.getColumnKey().equals("PRI")){
                    keycount++;
                }
            }
            if(keycount==1){//如果是只有一个主键
                System.out.println("读取表:"+table.getName());
                Element tableElement= root.addElement("table");
                tableElement.addAttribute("name",table.getName());
                tableElement.addAttribute("comment", Unicode.toUnicodeString(table.getComment()));
                tableElement.addAttribute("key",table.getKey());
                for (Column column:table.getColumns()){
                    System.out.println("读取字段："+column.getColumnName());
                    Element columnElement = tableElement.addElement("column");
                    columnElement.addAttribute("name",column.getColumnName());
                    columnElement.addAttribute("type",column.getColumnType());
                    columnElement.addAttribute("dbtype",column.getColumnDbType());
                    columnElement.addAttribute("comment",column.getColumnComment());
                    columnElement.addAttribute("decimal_digits",String.valueOf(column.getDecimal_digits()));
                    columnElement.addAttribute("colums_size",String.valueOf(column.getColums_size()));
                    //主键数量
                    if (column.getColumnKey().equals("PRI")){
                        keycount++;
                    }
                }
            }
        }
        writeXml(outPath,doc);

    }
    /*
    * outpath是输出路径，doc是包括数据库表的信息
    * */
    private static void writeXml(String outPath, Document doc){
        try {
            String xmlFileName = "db.xml";
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = null;
            File file = new File(outPath + File.separator + xmlFileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new XMLWriter(new FileWriter(file), format);
            writer.write(doc);
            writer.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
    }
    /*
    * 从xmlPath中读取转换为table对象
    * */
    public static List<Table> readDatabaseXml(String xmlPath){
        List<Table> list=new ArrayList<>();
        try{
            SAXReader reader=new SAXReader();
            Document doc = reader.read(xmlPath+File.separator+"db.xml");
            Element dbe = doc.getRootElement();
            List<Element> elements = dbe.elements();
            for(Element e:elements){
                if(e.getName().equals("table")){
                    Table table=new Table();
                    table.setName(e.attributeValue("name"));
                    table.setComment(Unicode.decodeUnicode(e.attributeValue("comment")));
                    table.setKey(e.attributeValue("key"));

                    List<Column> columns=new ArrayList<Column>();
                    List<Element> eList = e.elements();
                    for(Element e2:eList){
                        Column column = new Column();
                        column.setColumnName(e2.attributeValue("name"));
                        column.setColumnType(e2.attributeValue("type"));
                        column.setColumnDbType(e2.attributeValue("dbtype"));
                        column.setColumnComment(Unicode.decodeUnicode(e2.attributeValue("comment")));
                        column.setColumnKey(e2.attributeValue("key"));
                        columns.add(column);
                    }
                    table.setColumns(columns);
                    list.add(table);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /*
    * 读取db.xml中的property属性
    * */
    public static Map<String, String> readProperty(String xmlPath) {
        Map<String, String> map = new HashMap<>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(xmlPath + File.separator + "db.xml");
            Element rootElement = doc.getRootElement();
            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                if (element.getName().equals("property")) {
                    map.put(element.attributeValue("name"), element.getText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
