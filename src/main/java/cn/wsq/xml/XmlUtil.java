package cn.wsq.xml;

import cn.wsq.util.Unicode;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {
    /*
    * 根据Map中的内容，生成在filename文件名中
    * */
    public static void write(Map<String,String> map,String filename){
        Document document= DocumentHelper.createDocument();
        document.setXMLEncoding("utf-8");
        Element root = document.addElement("root");
        for(String key:map.keySet()) {
            Element mapElement = root.addElement("map");
            mapElement.addAttribute("key", key);
            mapElement.addAttribute("value", Unicode.toUnicodeString(map.get(key)));
        }
        File directory=new File("");//设定为当前目录
        writeXml(directory.getAbsolutePath()+File.separator+filename,document);
    }
    /*
     * 根据文件名读取key,value
     * */
    public static Map<String ,String> read(String filename){
        File directory=new File("");//设定为当前文件夹
        Map<String,String > map=new HashMap<String,String>();
        SAXReader reader=new SAXReader();
        try{
            Document doc=reader.read(directory.getAbsolutePath()+File.separator+filename);
            Element rootElement = doc.getRootElement();
            List<Element> elements = rootElement.elements();
            for(Element element:elements){
                map.put(element.attributeValue("key"),Unicode.decodeUnicode(element.attributeValue("value")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
    /*
    * 根据文件名读取key,value
    * */
    public static Map<String,String> readNu(String filename){
        File directory=new File("");
        Map<String,String> map=new HashMap<String, String>();
        SAXReader reader=new SAXReader();
        String str=directory.getAbsolutePath();
        try{
           Document doc=reader.read(directory.getAbsolutePath()+File.separator+filename);
            Element rootElement = doc.getRootElement();
            List<Element> elements = rootElement.elements();
            for(Element element:elements){
                map.put(element.attributeValue("key"),element.attributeValue("value"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /*
    * 根据document生成到outPath路径下
    * */
    private static void writeXml(String outPath, Document document) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = null;
            File file = new File(outPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new XMLWriter(new FileWriter(file),format);
            writer.write(document);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
